package de.tu_bs.cs.isf.cbc.tool.proofgraphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.xtext.util.Files;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.proorepository.FileSystemProofRepository;
import de.tu_bs.cs.isf.cbc.proorepository.IProofRepository;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStatement;
import de.tu_bs.cs.isf.cbc.tool.partialproof.VerifyStatementPartialProofComplete;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.IFileUtil;
import de.tu_bs.cs.isf.cbc.util.Parser;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraph;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofNode;

public class VerifyStatementProofGraphComplete extends MyAbstractAsynchronousCustomFeature {

	public VerifyStatementProofGraphComplete(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify a statement as proof node (completion)";
	}

	@Override
	public String getDescription() {
		return "Proofs this statement with the partial proof of the proof nodes";
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
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement) {
				AbstractStatement statement = (AbstractStatement) bo;
				
				String preFormula = Parser.getConditionFromCondition(statement.getPreCondition().getName());
				String postFormula = Parser.getConditionFromCondition(statement.getPostCondition().getName());
				
				if (preFormula.contains("original") || postFormula.contains("original")) {
					VerifyOriginalCallStatementProofGraphComplete verify = new VerifyOriginalCallStatementProofGraphComplete(getFeatureProvider());
					verify.execute(context);
				} else {
					// TODO: Dont generate products here just execute
					VerifyStatement verify = new VerifyStatement(getFeatureProvider());
					verify.execute(context);
				}
			}
		}

	}
	
	public void loadProofFileFromRepo(UUID uuid) {
		URI uri = this.getDiagram().eResource().getURI();
		IFileUtil fileHandler = new FileUtil(uri.toPlatformString(true));
		String location = fileHandler.getLocationString(getDiagram().eResource().getURI().toPlatformString(true)) + "/Statement1.key";
		
		IProofRepository proofRepository = new FileSystemProofRepository();
		String proofFile = proofRepository.getPartialProofForId(uuid);
		Files.writeStringIntoFile(location, proofFile);
	}

	public List<List<String>> generateAllPaths(ProofGraph graph) {
		List<List<String>> paths = new ArrayList<List<String>>();
		
		Set<ProofNode> pathStarts = graph.getAdjacencyList().keySet();
		
		graph.getAdjacencyList().forEach((__, entry) -> {
			pathStarts.removeAll(entry);
		});
		
		for (ProofNode node : pathStarts) {
			List<String> localPathList = new ArrayList<String>();
			localPathList.add(node.getFeature());
			findPaths(graph, node, paths, localPathList);
		}
		
		return paths;
	}
	
	public void findPaths(ProofGraph graph, ProofNode node, List<List<String>> paths, List<String> localPathList) {
		if (graph.getAdjacencyList().get(node).isEmpty()) {
			paths.add(List.copyOf(localPathList));
			return;
		}

		for (ProofNode cn : graph.getAdjacencyList().get(node)) {
			localPathList.add(cn.getFeature());
			findPaths(graph, cn, paths, localPathList);
			localPathList.remove(cn.getFeature());
		}
	}
	
	public Set<String[]> convertArrays(Set<List<String>> old) {
		Set<String[]> newSet = new HashSet<String[]>();
		
		for(List<String> oldList : old) {
			String[] array = new String[oldList.size()];
			for (int i = 0; i < oldList.size(); i++)
				array[i] = oldList.get(i);
			newSet.add(array);
		}
		
		return newSet;
	}
	
}