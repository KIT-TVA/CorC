package de.tu_bs.cs.isf.cbc.mutation.feature;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.exceptions.CodeRepresentationFinderException;
import de.tu_bs.cs.isf.cbc.exceptions.FileHandlerException;
import de.tu_bs.cs.isf.cbc.exceptions.MutatorException;
import de.tu_bs.cs.isf.cbc.mutation.util.BoilerplateRemover;
import de.tu_bs.cs.isf.cbc.mutation.util.CodeRepresentationFinder;
import de.tu_bs.cs.isf.cbc.mutation.util.DirectoryCreator;
import de.tu_bs.cs.isf.cbc.mutation.util.MutatedClass;
import de.tu_bs.cs.isf.cbc.mutation.util.MutationInformationTripel;
import de.tu_bs.cs.isf.cbc.util.ClassHandler;
import de.tu_bs.cs.isf.cbc.util.CodeGenerator;
import de.tu_bs.cs.isf.cbc.util.CodeHandler;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.CopyDiagram;
import de.tu_bs.cs.isf.cbc.util.DiffChecker;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.LinePair;
import de.tu_bs.cs.isf.cbc.util.MethodHandler;
import src.mujava.AllMutantsGenerator;
import src.mujava.MutationSystem;
import src.mujava.OpenJavaException;
import src.mujava.makeMuJavaStructure;

public class ImplMutator extends Mutator {
    private BoilerplateRemover boilerRemover = new BoilerplateRemover();
    private String originalCode;

    @Override
    public void mutate(Diagram diagramToMutate, Condition condition) throws Exception {
	setup(diagramToMutate);
	originalCode = constructCode();
	generateFiles();
	File[] mutants = getMutants();
	this.mutants = readCode(mutants);
	cleanUp();
	generateDiagrams();
	this.saveMutationInformation();
	ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
    }

    @Override
    protected void generateDiagrams() throws CodeRepresentationFinderException, IOException, CoreException,
	    MutatorException, FileHandlerException {
	DiffChecker dc = new DiffChecker();
	for (String mutant : mutants) {
	    dc.check(getOriginalCode(), mutant);
	    LinePair diffLine = dc.nextDiff();
	    applyMutation(diffLine);
	    this.mutantInfos.add(new MutationInformationTripel(diffLine, this.mutantBaseName + this.mutationCount));
	}
	MutatedClass mc = new MutatedClass(this);
	mc.generate();
    }

    protected ImplMutator(List<String> operators) {
	super(operators);
    }

    protected void generateFiles() throws IOException, OpenJavaException {
	String location = setupMuJava();
	File targetFile = generateFile(location, getOriginalDiagram().getName(), originalCode);
	executeMuJava(targetFile);
    }

    protected File generateFile(String location, String name, String code) throws IOException {
	location = location + File.separator + "src" + File.separator + name + ".java";
	File f = new File(location);
	f.createNewFile();
	FileWriter fw = new FileWriter(f);
	fw.write(code);
	fw.close();
	FileUtils.copyFile(f, new File(MutationSystem.CLASS_PATH + File.separator + f.getName()));
	return f;
    }

    protected String getOriginalCode() {
	if (originalCode == null) {
	    return "";
	}
	return CodeHandler.indentCode(originalCode, 0);
    }

    protected String constructCode() throws Exception {
	ClassHandler ch = new ClassHandler(this.className, this.classUri);
	String diagramAsCode = CodeGenerator.instance.generateCodeFor(getOriginalDiagram());
	String signature = MethodHandler.getSignatureFromCode(diagramAsCode);
	String contract = MethodHandler.getContractFromCode(diagramAsCode, signature);
	MethodHandler mh = new MethodHandler(contract, signature, diagramAsCode);
	ch.addMethod(mh);
	return ch.getCode();
    }

    protected String setupMuJava() {
	String location = FileUtil.getProjectLocation(getOriginalDiagram().eResource().getURI()) + File.separator
		+ getFolderName();
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
	String[] classNames = new String[] { "" };
	AllMutantsGenerator allgen = new AllMutantsGenerator(mutationTargetFile, classNames, operators);
	allgen.makeMutants();
    }

    protected void applyMutation(LinePair diffLine)
	    throws CodeRepresentationFinderException, IOException, CoreException, MutatorException {
	this.mutationCount++;
	String mutantName = this.mutantBaseName + this.mutationCount;
	String diagCopyPath = this.mutationFolder.getLocation().toOSString() + File.separator + mutantName;// diagPath;//this.mutationFolder.getLocation().toOSString()
													   // +
													   // File.separator
													   // +
													   // mutantName;
	CopyDiagram cd = new CopyDiagram(diagCopyPath, this.getOriginalDiagram(), mutantName);
	Resource res = cd.getResource();
	if (changeTargetLine(res, diffLine)) {
	    cd.save();
	}
	this.getMutantDiagrams().add(cd.getDiagram());
    }

    protected boolean changeTargetLine(Resource resource, LinePair diffLine)
	    throws CodeRepresentationFinderException, IOException, MutatorException {
	CbCFormula formula = getFormulaFrom(resource);
	unproveEverything(formula.getStatement());
	AbstractStatement firstStatement = formula.getStatement();
	CodeRepresentationFinder crf = new CodeRepresentationFinder();
	EObject target = crf.find(firstStatement, diffLine.originalLine);
	if (diffLine.originalLine.contains("else")) {
	    var js = 3;
	}
	if (target instanceof SelectionStatement) {
	    var selS = (SelectionStatement) target;
	    var repParsed = boilerRemover.removeIn(selS.getCodeRepresentation());
	    var newParsed = boilerRemover.removeIn(diffLine.newLine);
	    for (var guard : selS.getGuards()) {
		if (guard.getName().equals(repParsed)) {
		    guard.setName(newParsed);
		}
	    }
	} else if (target instanceof SmallRepetitionStatement) {
	    var repS = (SmallRepetitionStatement) target;
	    var newParsed = boilerRemover.removeIn(diffLine.newLine);
	    repS.getGuard().setName(newParsed);
	    ((AbstractStatement) target.eContainer()).setProven(false);
	} else if (target instanceof AbstractStatement) {
	    AbstractStatement targetAs = (AbstractStatement) target;
	    targetAs.setName(targetAs.getName().replace(crf.stripOriginal(diffLine.originalLine),
		    crf.stripOriginal(diffLine.newLine)));
	    targetAs.setProven(false);
	    ((AbstractStatement) target.eContainer()).setProven(false);
	} else if (target instanceof Condition) {
	    Condition targetC = (Condition) target;
	    if (crf.isContract()) {
		targetC.setName(crf.transformJMLToJavaDL(diffLine.newLine));
	    } else {
		targetC.setName(boilerRemover.removeIn(diffLine.newLine));
	    }
	    ((AbstractStatement) target.eContainer()).setProven(false);
	} else {
	    Console.println("Mutation of line '" + diffLine.originalLine + "' ignored.");
	    resource.save(Collections.EMPTY_MAP);
	    return false;
	}
	resource.save(Collections.EMPTY_MAP);
	return true;
    }

}
