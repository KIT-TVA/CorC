package de.tu_bs.cs.isf.cbc.mutation.feature;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.exceptions.CodeRepresentationFinderException;
import de.tu_bs.cs.isf.cbc.exceptions.MutatorException;
import de.tu_bs.cs.isf.cbc.mutation.util.CodeRepresentationFinder;
import de.tu_bs.cs.isf.cbc.mutation.util.CopyDiagram;
import de.tu_bs.cs.isf.cbc.mutation.util.DirectoryCreator;
import de.tu_bs.cs.isf.cbc.mutation.util.MutatedClass;
import de.tu_bs.cs.isf.cbc.tool.helper.ClassHandler;
import de.tu_bs.cs.isf.cbc.tool.helper.CodeGenerator;
import de.tu_bs.cs.isf.cbc.tool.helper.CodeHandler;
import de.tu_bs.cs.isf.cbc.tool.helper.DiffChecker;
import de.tu_bs.cs.isf.cbc.tool.helper.LinePair;
import de.tu_bs.cs.isf.cbc.tool.helper.MethodHandler;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import src.mujava.AllMutantsGenerator;
import src.mujava.MutationSystem;
import src.mujava.OpenJavaException;
import src.mujava.makeMuJavaStructure;

public class ImplMutator extends Mutator {
	private String className;
	private String originalCode;
	private String[] mutants; 
	private String[] mutantNames;
	
	public ImplMutator(List<String> operators) {
		super(operators);
	}
	
	@Override
	public void mutate(Diagram diagramToMutate) throws Exception {
		originalDiagram = diagramToMutate;
		getClassInformation();
		originalCode = constructCode();
		setup();
		generateFiles();
		File[] mutants = getMutants();
		this.mutants = readCode(mutants);
		this.mutantNames = new String[mutants.length];
		cleanUp();
	}

	@Override
	public void generateDiagrams() throws CodeRepresentationFinderException, IOException, CoreException, MutatorException {
		String originalCode = getOriginalCode();
		DiffChecker dc = new DiffChecker();
		for (String mutant : mutants) {
			dc.check(originalCode, mutant);
			LinePair diffLine = dc.nextDiff();	
			applyMutation(diffLine);
		}
		MutatedClass mc = new MutatedClass(this.originalDiagram, mutantNames);
		mc.generate();
	}
	

	protected void generateFiles() throws IOException, OpenJavaException {
		String location = setupMuJava();
		File targetFile = generateFile(location, originalDiagram.getName(), originalCode);
		executeMuJava(targetFile);
	}

	protected String getOriginalCode() {
		if (originalCode == null) {
			return "";
		}
		return CodeHandler.indentCode(originalCode, 0);
	}
	
	protected void getClassInformation() {
		className = originalDiagram.eResource().getURI().segment(originalDiagram.eResource().getURI().segmentCount() - 2);
		classUri = originalDiagram.eResource().getURI();
	}
	
	protected String constructCode() throws Exception {
		ClassHandler ch = new ClassHandler(this.className, this.classUri);
		String diagramAsCode = CodeGenerator.getInstance().generateCodeFor(originalDiagram);
		String signature = MethodHandler.getSignatureFromCode(diagramAsCode);
		String contract = MethodHandler.getContractFromCode(diagramAsCode, signature);
		MethodHandler mh = new MethodHandler(contract, signature, diagramAsCode);
		ch.addMethod(mh);
		return ch.getCode();
	}
	
	protected String setupMuJava() {
		String location = FileUtil.getProjectLocation(originalDiagram.eResource().getURI()) + File.separator + FOLDER_NAME;
		MutationSystem.CLASS_NAME = className;
		setMuJavaPaths(location);
		makeMuJavaStructure.main(null);
		return location;
	}
	
	protected void setMuJavaPaths(String location) {
		MutationSystem.SYSTEM_HOME = location;
		MutationSystem.SRC_PATH = location + File.separator + "src";
		MutationSystem.CLASS_PATH = location + File.separator + "classes"; 
        MutationSystem.TESTSET_PATH = location + File.separator + "testset";
		MutationSystem.MUTANT_HOME = location + File.separator + "result";
		MutationSystem.MUTANT_PATH = MutationSystem.MUTANT_HOME;
		MutationSystem.TRADITIONAL_MUTANT_PATH = MutationSystem.MUTANT_PATH;
		MutationSystem.CLASS_MUTANT_PATH = MutationSystem.MUTANT_PATH;
		MutationSystem.EXCEPTION_MUTANT_PATH = MutationSystem.MUTANT_PATH;
		DirectoryCreator.makeDir(new File(location));
	}
	
	protected void executeMuJava(File mutationTargetFile) throws OpenJavaException {
		String[] classNames = new String[] {""};
		AllMutantsGenerator allgen = new AllMutantsGenerator(mutationTargetFile, classNames, operators);
		allgen.makeMutants();
	}
	
	protected void applyMutation(LinePair diffLine) throws CodeRepresentationFinderException, IOException, CoreException, MutatorException {
		this.mutationCount++;
		String mutantName = this.originalDiagram.getName() + "Mutant" + this.mutationCount;
		this.mutantNames[this.mutationCount-1] = mutantName;
		String diagCopyPath = this.mutationFolder.getLocation().toOSString() + File.separator + mutantName;
		CopyDiagram cd = new CopyDiagram(diagCopyPath, this.originalDiagram, mutantName);
		Resource res = cd.getResource();
		if (changeTargetLine(res, diffLine)) {
			cd.save();
		}
	}
	
	protected boolean changeTargetLine(Resource resource, LinePair diffLine) throws CodeRepresentationFinderException, IOException, MutatorException {
		CbCFormula formula = null;
		for (EObject o : resource.getContents()) {
			if (o instanceof CbCFormula) {
				formula = (CbCFormula)o;
				break;
			}
		}
		AbstractStatement firstStatement = formula.getStatement();
		CodeRepresentationFinder crf = new CodeRepresentationFinder();
		EObject target = crf.find(firstStatement, diffLine.originalLine);
		if (target instanceof AbstractStatement) {
			AbstractStatement targetAs = (AbstractStatement)target;
			targetAs.setName(targetAs.getName().replace(diffLine.originalLine, diffLine.newLine));
			targetAs.setProven(false);
		} else if (target instanceof Condition) {
			Condition targetC = (Condition)target;
			if (crf.isContract()) {
				targetC.setName(crf.transformJMLToJavaDL(diffLine.newLine));
			} else {
				targetC.setName(targetC.getName().replace(diffLine.originalLine, diffLine.newLine));
			}
			((AbstractStatement)target.eContainer()).setProven(false);
		} else {
			Console.println("Mutation of line '" + diffLine.originalLine + "' ignored.");
			resource.save(Collections.EMPTY_MAP);
			return false;
		}
		resource.save(Collections.EMPTY_MAP);
		return true;
	}
	
}
