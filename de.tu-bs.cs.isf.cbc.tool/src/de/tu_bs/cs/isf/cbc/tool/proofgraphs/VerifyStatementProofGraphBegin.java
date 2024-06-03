package de.tu_bs.cs.isf.cbc.tool.proofgraphs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.ovgu.featureide.fm.core.analysis.cnf.CNF;
import de.ovgu.featureide.fm.core.analysis.cnf.LiteralSet;
import de.ovgu.featureide.fm.core.analysis.cnf.LiteralSet.Order;
import de.ovgu.featureide.fm.core.analysis.cnf.Variables;
import de.ovgu.featureide.fm.core.analysis.cnf.formula.FeatureModelFormula;
import de.ovgu.featureide.fm.core.analysis.cnf.solver.AdvancedSatSolver;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.SelectableFeature;
import de.ovgu.featureide.fm.core.configuration.Selection;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.FileSystemProofRepository;
import de.tu_bs.cs.isf.cbc.tool.IProofRepository;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.Colors;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.FileHandler;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.IFileUtil;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraphCollection;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofNode;

public class VerifyStatementProofGraphBegin extends MyAbstractAsynchronousCustomFeature {

	public VerifyStatementProofGraphBegin(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify a statement as proof node (begin)";
	}

	@Override
	public String getDescription() {
		return "Creates the partial proof for all feature nodes in this method";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		//TODO: Only show when SPL (check for variational)
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && (bo.getClass().equals(AbstractStatementImpl.class) || bo instanceof SkipStatement
					|| bo instanceof ReturnStatement)) {
				AbstractStatement statement = (AbstractStatement) bo;
				if (statement.getRefinement() == null) {
					ret = true;
				}
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		long startTime = System.nanoTime();
		PictogramElement[] pes = context.getPictogramElements();
		
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement statement) {
				URI uri = this.getDiagram().eResource().getURI();
				FileHandler.instance.deleteFolder(uri, "tests");
				
				IProject project = FileUtil.getProjectFromFileInProject(uri);

				String callingFeature = FeatureUtil.getInstance().getCallingFeature(uri);
				String callingClass = FeatureUtil.getInstance().getCallingClass(uri);
				String callingMethod = FeatureUtil.getInstance().getCallingMethod(uri);
				
				GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(super.getFeatureProvider());
				
				String[] featureConfig = this.findValidProduct(callingFeature, project);
				
				Console.println(Arrays.toString(featureConfig));
				
				genCode.setProofTypeInfo(0, KeYInteraction.ABSTRACT_PROOF_BEGIN);

				genCode.generate(project.getLocation(), 
						callingFeature, callingClass, callingMethod, 
						featureConfig);

				IFileUtil fileHandler = new FileUtil(uri.toPlatformString(true));
				
				ProveWithKey prove = new ProveWithKey(statement, getDiagram(), monitor, fileHandler, featureConfig, 0, KeYInteraction.ABSTRACT_PROOF_BEGIN);
				boolean proven = prove.proveStatementWithKey(null, null, null, false, false, callingMethod, "", callingClass, true);
				statement.setProven(proven);
				
				//Somehow find Statement.key or always Statement1? TODO: Think about it
				String location = fileHandler.getLocationString(getDiagram().eResource().getURI().toPlatformString(true)) + "/Statement1.key";

				//Save to Proof repository
				try {
					ProofGraphCollection collection = ProofGraphCollection.loadFromJson(project.getRawLocation() + "/graph.json");
					ProofNode node = collection.getProofNode(callingMethod, callingFeature);
					String proofText;
					proofText = Files.readString(java.nio.file.Path.of(location));
					IProofRepository proofRepo = new FileSystemProofRepository();
					proofRepo.savePartialProofForId(node.getId(), proofText);
					Console.print("Saved proof to Proof Repository as " + node.getId(), Colors.GREEN);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1_000_000;
		Console.println("\nVerification done."); 
		Console.println("Time needed: " + duration + "ms");
	}
	
	
	private String[] findValidProduct(String callingFeature, IProject project) {
			IFeatureModel featureModel = FeatureModelManager.load(Paths.get(project.getLocation() + "/model.xml"));
			Configuration configuration = new Configuration(new FeatureModelFormula(featureModel));
			configuration.setManual(callingFeature, Selection.SELECTED);
			CNF cnf = configuration.getFeatureModelFormula().getCNF();
			AdvancedSatSolver solver = new AdvancedSatSolver(cnf);
			Variables vars = cnf.getVariables();
			for (SelectableFeature current : configuration.getFeatures()) {
				if (current.getSelection() != Selection.UNDEFINED) {
					solver.assignmentPush(
						vars.getVariable(
								current.getFeature().getName(), 
								current.getSelection() == Selection.SELECTED));
				}
			}

			int[] solution = solver.findSolution();
			if (solution == null) return null;
			final LiteralSet result = new LiteralSet(solution, Order.INDEX, false);
			Configuration config = new Configuration(configuration, configuration.getFeatureModelFormula());
			for (int literal : result.getLiterals()) {
				SelectableFeature feature = config.getSelectableFeature(vars.getName(literal));
				if (feature.getSelection() == Selection.UNDEFINED) {
					config.setManual(feature, literal > 0 ? Selection.SELECTED : Selection.UNSELECTED);
				}
			}
			
			String[] featureConfig = new String[config.getSelectedFeatures().size()];
			for (int i = 0; i < featureConfig.length; i++) {
				featureConfig[i] = config.getSelectedFeatures().get(i).getName();
			}
			
			return featureConfig;
	}
}