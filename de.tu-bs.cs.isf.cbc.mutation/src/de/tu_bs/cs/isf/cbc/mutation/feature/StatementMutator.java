package de.tu_bs.cs.isf.cbc.mutation.feature;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.exceptions.FileHandlerException;
import de.tu_bs.cs.isf.cbc.exceptions.MutatorException;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;
import de.tu_bs.cs.isf.cbc.mutation.util.DirectoryCreator;
import de.tu_bs.cs.isf.cbc.mutation.util.MutatedClass;
import de.tu_bs.cs.isf.cbc.mutation.util.MutationInformationTripel;
import de.tu_bs.cs.isf.cbc.util.ClassHandler;
import de.tu_bs.cs.isf.cbc.util.CodeHandler;
import de.tu_bs.cs.isf.cbc.util.CopyDiagram;
import de.tu_bs.cs.isf.cbc.util.DiagramPartsExtractor;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.LinePair;
import src.mujava.AllMutantsGenerator;
import src.mujava.MutationSystem;
import src.mujava.OpenJavaException;
import src.mujava.makeMuJavaStructure;

public class StatementMutator extends Mutator {
    protected StatementMutator(List<String> operators) {
	super(operators);
    }

    @Override
    public void mutate(Diagram diagramToMutate, Condition condition) throws Exception {
	setup(diagramToMutate);
	mutateStatements();
	this.saveMutationInformation();
	ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
    }

    private CopyDiagram copyDiagram(final Diagram diagram) throws IOException {
	this.mutationCount++;
	String mutantName = this.mutantBaseName + this.mutationCount;
	String diagCopyPath = this.mutationFolder.getLocation().toOSString() + File.separator + mutantName;
	CopyDiagram cd = new CopyDiagram(diagCopyPath, diagram, mutantName);
	cd.save();
	return cd;
    }

    private void mutateStatements() throws MutatorException, IOException, CoreException, FileHandlerException,
	    OpenJavaException, SettingsException {
	DiagramPartsExtractor dpe = new DiagramPartsExtractor(this.getOriginalDiagram());
	var formula = dpe.getFormula();
	if (formula == null)
	    throw new MutatorException("Couldn't load formula of diagram " + this.getOriginalDiagram().getName() + ".");
	var mutatables = new ArrayList<EObject>();
	getMutatables(mutatables, formula.getStatement().getRefinement());
	for (var mutatable : mutatables) {
	    generateMutantDiagramsFor(mutatable);
	}
	MutatedClass mc = new MutatedClass(this);
	mc.generate();
    }

    private void generateMutantDiagramsFor(EObject mutatable)
	    throws IOException, OpenJavaException, CoreException, MutatorException, SettingsException {
	var code = generateDummyCode(mutatable);
	generateMutants(code);
	var mutants = getMutantCodes();
	for (var mutant : mutants) {
	    var cd = copyDiagram(this.getOriginalDiagram());
	    applyMutation(cd.getDiagram(), mutatable, mutant);
	    cd.getResource().save(Collections.EMPTY_MAP);
	    this.getMutantDiagrams().add(cd.getDiagram());
	}

    }

    private void getMutatables(final List<EObject> mutatables, final EObject current) {
	if (current == null) {
	    return;
	}
	if (current instanceof SmallRepetitionStatement) {
	    var statement = (SmallRepetitionStatement) current;
	    mutatables.add(statement.getGuard());
	} else if (current instanceof SelectionStatement) {
	    var statement = (SelectionStatement) current;
	    mutatables.addAll(statement.getGuards());
	} else if (current instanceof AbstractStatement) {
	    if ((((AbstractStatement) current).getName()).contains(";")) {
		mutatables.add(current);
	    }
	}
	for (var child : current.eContents()) {
	    getMutatables(mutatables, child);
	}
    }

    private String generateDummyCode(EObject mutatable) throws SettingsException {
	ClassHandler ch = new ClassHandler(this.className, this.classUri);
	var classCode = ch.getCode(false);
	var code = classCode.substring(0, classCode.length() - 1);
	code += genDummyMethodHead();
	code += "{\n";
	if (mutatable instanceof Condition) {
	    var mut = (Condition) mutatable;
	    code += "boolean test = " + mut.getName() + ";";
	} else {
	    var statement = (AbstractStatement) mutatable;
	    code += statement.getName();
	}
	code += "\n}\n}";
	return code;
    }

    private String genDummyMethodHead() {
	String method = "public void dummy(";
	var dpe = new DiagramPartsExtractor(this.getOriginalDiagram());
	var vars = dpe.getVars();
	boolean added = false;
	for (var v : vars.getVariables()) {
	    method += v.getName() + ", ";
	    added = true;
	}
	for (var p : vars.getParams()) {
	    method += p.getType() + " " + p.getName() + ", ";
	    added = true;
	}
	for (var f : vars.getFields()) {
	    method += f.getType() + " " + f.getName() + ", ";
	    added = true;
	}
	if (!added)
	    return method + ")";
	method = method.substring(0, method.length() - ", ".length()) + ")";
	return method;
    }

    private void setMuJavaPaths(String location) {
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

    private String setupMuJava() {
	String location = FileUtil.getProjectLocation(getOriginalDiagram().eResource().getURI()) + File.separator
		+ getFolderName();
	MutationSystem.CLASS_NAME = className;
	setMuJavaPaths(location);
	makeMuJavaStructure.main(null);
	return location;
    }

    private void generateMutants(String code) throws IOException, OpenJavaException {
	String location = setupMuJava();
	location = location + File.separator + "src" + File.separator + this.getOriginalDiagram().getName() + ".java";
	File f = new File(location);
	f.createNewFile();
	FileWriter fw = new FileWriter(f);
	fw.write(code);
	fw.close();
	FileUtils.copyFile(f, new File(MutationSystem.CLASS_PATH + File.separator + f.getName()));
	executeMuJava(f);
    }

    private void executeMuJava(File mutationTargetFile) throws OpenJavaException {
	String[] classNames = new String[] { "" };
	AllMutantsGenerator allgen = new AllMutantsGenerator(mutationTargetFile, classNames, operators);
	allgen.makeMutants();
    }

    private void applyMutation(final Diagram diagram, final EObject mutatee, String mutant)
	    throws IOException, MutatorException {
	EObject target = findMutatee(diagram, mutatee);
	mutant = extractMutation(mutant);
	if (target instanceof Condition) {
	    ((AbstractStatement) target.eContainer()).setProven(false);
	    ((Condition) target).setName(mutant.substring(0, mutant.length()));
	} else {
	    ((AbstractStatement) target).setProven(false);
	    ((AbstractStatement) target).setName(mutant);
	}
	LinePair diffLine = new LinePair(getName(mutatee), mutant);
	this.mutantInfos.add(new MutationInformationTripel(diffLine, this.mutantBaseName + this.mutationCount));
	target.eResource().save(Collections.EMPTY_MAP);
    }

    private String getName(EObject target) {
	if (target instanceof Condition) {
	    return ((Condition) target).getName();
	} else {
	    return ((AbstractStatement) target).getName();
	}
    }

    private String extractMutation(String mutationCode) {
	String conditionTag = "boolean test = ";
	String mutation;
	if (mutationCode.contains(conditionTag)) {
	    mutation = mutationCode.substring(mutationCode.indexOf(conditionTag) + conditionTag.length(),
		    mutationCode.length());
	    mutation = mutation.substring(0, mutation.indexOf(";"));
	} else {
	    var dummyMethodStart = mutationCode.lastIndexOf("{");
	    var dummyMethodEnd = CodeHandler.findClosingBracketIndex(mutationCode, dummyMethodStart, '{');
	    mutation = mutationCode.substring(dummyMethodStart + 1, dummyMethodEnd).trim();
	}
	return mutation;
    }

    private EObject findMutatee(final Diagram diagram, final EObject mutatee) throws MutatorException {
	var dpe = new DiagramPartsExtractor(diagram);
	var target = findIn(dpe.getFormula().getStatement().getRefinement(), mutatee);
	if (target == null)
	    throw new MutatorException("Couldn't find '" + mutatee + "' in copied diagram.");
	return target;
    }

    private EObject findIn(EObject current, EObject target) {
	if (current instanceof SmallRepetitionStatement) {
	    EObject rep = checkRepetition(current, target);
	    if (rep != null) {
		return rep;
	    }
	} else if (current instanceof CompositionStatement) {
	    EObject comp = checkComposition(current, target);
	    if (comp != null) {
		return comp;
	    }
	} else if (current instanceof SelectionStatement) {
	    EObject sel = checkSelection(current, target);
	    if (sel != null) {
		return sel;
	    }
	} else if (current instanceof AbstractStatement && target instanceof AbstractStatement) {
	    var currentS = (AbstractStatement) current;
	    var targetS = (AbstractStatement) target;
	    if (currentS.getName().equals(targetS.getName())) {
		return currentS;
	    }
	} else if (current instanceof Condition && target instanceof Condition) {
	    var currentC = (Condition) current;
	    var targetC = (Condition) target;
	    if (currentC.getName().equals(targetC.getName())) {
		return currentC;
	    }
	}
	for (EObject child : current.eContents()) {
	    return findIn(child, target);
	}
	return null;
    }

    private EObject checkRepetition(EObject current, EObject target) {
	if (target instanceof Condition) {
	    var targetC = (Condition) target;
	    var currentG = (Condition) ((SmallRepetitionStatement) current).getGuard();
	    if (currentG.getName().equals(targetC.getName())) {
		return currentG;
	    }
	}
	return findIn(((SmallRepetitionStatement) current).getLoopStatement(), target);
    }

    private EObject checkComposition(EObject current, EObject target) {
	if (target instanceof Condition) {
	    var targetC = (Condition) target;
	    if (((CompositionStatement) current).getIntermediateCondition().getName().equals(targetC.getName())) {
		return current;
	    }
	}
	var conInFirst = findIn(((CompositionStatement) current).getFirstStatement(), target);
	var conInSecond = findIn(((CompositionStatement) current).getSecondStatement(), target);
	return conInFirst != null ? conInFirst : conInSecond;
    }

    private EObject checkSelection(EObject current, EObject target) {
	var currentS = (SelectionStatement) current;
	if (target instanceof Condition) {
	    var targetC = (Condition) target;
	    for (var guard : currentS.getGuards()) {
		if (guard.getName().equals(targetC.getName())) {
		    return guard;
		}
	    }
	}
	for (AbstractStatement c : currentS.getCommands()) {
	    var con = findIn(c, target);
	    if (con != null) {
		return con;
	    }
	}
	return null;
    }
}
