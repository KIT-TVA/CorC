package de.tu_bs.cs.isf.cbc.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.key_project.util.collection.ImmutableSet;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
import de.uka.ilkd.key.control.KeYEnvironment;
import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.java.abstraction.KeYJavaType;
import de.uka.ilkd.key.logic.op.IObserverFunction;
import de.uka.ilkd.key.proof.Goal;
import de.uka.ilkd.key.proof.Proof;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;
import de.uka.ilkd.key.settings.ChoiceSettings;
import de.uka.ilkd.key.settings.ProofSettings;
import de.uka.ilkd.key.speclang.Contract;
import de.uka.ilkd.key.strategy.StrategyProperties;
import de.uka.ilkd.key.util.KeYTypeUtil;
import de.uka.ilkd.key.util.MiscTools;

public class ProveWithKey {

//private static int proofCounter = 0;

	public static boolean proveStatementWithKey(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProveStatementWithKey(statement, vars, conds, renaming, uri, 0, true);
		Console.println("Verify Pre -> {Statement} Post");
		return proveWithKey(location, monitor);
	}
	
	public static boolean proveStatementWithKey2(String className, AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProveStatementWithKey2(className, statement, vars, conds, renaming, uri, 0, true);
		Console.println("Verify Pre -> {Statement} Post");
		return proveWithKey(location, monitor);
	}
	
	public static File createProveStatementWithKey(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, URI uri, int numberFile, boolean override) {
		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				programVariablesString += var.getName() + "; ";
			}
		}
		
		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}
		
		IProject thisProject = getProject(uri);
		
		String pre = statement.getPreCondition().getName();
		String post = statement.getPostCondition().getName();
		String stat = statement.getName();
		
		if (pre == null || pre.length() == 0) {
			pre = "true";
		}
		if (post == null || post.length() == 0) {
			post = "true";
		}
		
		if(renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			pre = useRenamingCondition(renaming, pre);
			post = useRenamingCondition(renaming, post);
			stat = useRenamingStatement(renaming, stat);
		}
		
		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + " Heap heapAtPre;}"
				+ "\\problem {(" + pre + " " + globalConditionsString + ") -> {heapAtPre := heap} \\<{" + stat + "}\\> (" + post + globalConditionsString + ")}";

		String location = thisProject.getLocation() + "/src/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = writeFile(problem, location, numberFile, override);
		return keyFile;
	}
	
	public static File createProveStatementWithKey2(String className, AbstractStatement statement, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, int numberFile,
			boolean override) {

		String programVariablesString = "";
		String conditionArraysCreated = "";
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				programVariablesString += var.getName() + "; ";
				// if variable is an Array add <created> condition for key
				if (var.getName().contains("[]")) {
					String varName = var.getName().substring(var.getName().indexOf(" ") + 1);
					conditionArraysCreated += " & " + varName + ".<created>=TRUE";
				}

			}
		}

		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}

		IProject thisProject = getProject(uri);

		String assignmentString = "";
		String pre = statement.getPreCondition().getName();
		String post = statement.getPostCondition().getName();
		String stat = statement.getName();

		if (pre == null || pre.length() == 0) {
			pre = "true";
		}
		if (post == null || post.length() == 0) {
			post = "true";
		}

		if (renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			pre = useRenamingCondition(renaming, pre);
			post = useRenamingCondition(renaming, post);
			stat = useRenamingStatement(renaming, stat);
		}

		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";" + "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + " Heap heapAtPre;}" + "\\problem {(" + pre + " "
				+ globalConditionsString + conditionArraysCreated + " & wellFormed(heap) & self != null & self.<inv> & self.<created> = TRUE & " + className +   "::exactInstance(self)=TRUE) -> {heapAtPre := heap"
				+ assignmentString + "} \\<{" + stat + "}\\> (" + post + " & self.<inv>)}";

		String location = thisProject.getLocation() + "/src/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = writeFile(problem, location, numberFile, override);
		return keyFile;
	}

	public static IProject getProject(URI uri) {
		uri = uri.trimFragment();
		String uriPath = uri.toPlatformString(true);
		uriPath = uriPath.substring(1, uriPath.length());
		int positionOfSlash = uriPath.indexOf('/') + 1;
		uriPath = uriPath.substring(positionOfSlash, uriPath.length());
		IProject thisProject = null;
		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (p.getFile(new Path(uriPath)).exists()) {
				thisProject = p;
			}
		}
//		if (thisProject.getName().contains("Userstudy")) {
//			File diagramFile = new File(thisProject.getLocation() + "/" + uriPath);
//			File diagramFileCopy = new File(thisProject.getLocation() + "/src/saved/ExDia" + proofCounter +  ".diagram");
//			File cbcFile = new File(thisProject.getLocation() + "/" + uriPath.substring(0, uriPath.indexOf(".")) + ".cbcmodel");
//			File cbcFileCopy = new File(thisProject.getLocation() + "/src/saved/ExDia" + proofCounter +  ".cbcmodel");
//			proofCounter++;
//			try {
//				IWorkspace workspace = ResourcesPlugin.getWorkspace();
//				Files.copy(diagramFile.toPath(), diagramFileCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);
//				Files.copy(cbcFile.toPath(), cbcFileCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);  
//				IPath iLocation = Path.fromOSString(diagramFileCopy.getAbsolutePath()); 
//				IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
//				ifile.refreshLocal(0, null);
//				iLocation = Path.fromOSString(cbcFileCopy.getAbsolutePath()); 
//				ifile = workspace.getRoot().getFileForLocation(iLocation);
//				ifile.refreshLocal(0, null);
//			} catch (IOException | CoreException e) {
//				e.printStackTrace();
//			}
//		}
		return thisProject;
	}
	
	private static File writeFile(String problem, String location, int numberFile, boolean override) {
		File keyFile = new File(location + "/prove" + numberFile + ".key");
		File keyHelperFile = new File(location + "/helper.key");
		if (!keyFile.exists() || override) {
			try {
				keyFile.getParentFile().mkdirs();
				keyFile.createNewFile();
				FileWriter fw = new FileWriter(keyFile);
				BufferedWriter bw = new BufferedWriter(fw);
	
				bw.write(problem);
				bw.close();
				
				if (!keyHelperFile.exists()) {
					keyHelperFile.createNewFile();
				}
				IWorkspace workspace = ResourcesPlugin.getWorkspace();    
				IPath iLocation = Path.fromOSString(keyFile.getAbsolutePath()); 
				IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
				ifile.refreshLocal(0, null);
				return keyFile;
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static boolean proveWithKey(File location, IProgressMonitor monitor) {
		Proof proof = createKeyProof(location, monitor);
		if (proof != null) {
			// Show proof result
			boolean closed = proof.openGoals().isEmpty();
			Console.println("Proof is closed: " + closed);
			if(!closed) {
				MainWindow.getInstance().loadProblem(location);
				MainWindow.getInstance().setVisible(true);
			}
			return closed;
		}
		return false;
	}

	private static String useRenamingCondition(Renaming renaming, String toRename) {
		for (Rename rename : renaming.getRename()) {
			if (rename.getType().equalsIgnoreCase("boolean")) {
				toRename = toRename.replaceAll(rename.getNewName(), "TRUE=" + rename.getFunction());
			} else {
				toRename = toRename.replaceAll(rename.getNewName(), rename.getFunction());
			}
		}
		return toRename;
	}
	
	private static String useRenamingCondition(List<Rename> renaming, String toRename) {
		for (Rename rename : renaming) {
			if (rename.getType().equalsIgnoreCase("boolean")) {
				toRename = toRename.replaceAll(rename.getNewName(), "TRUE=" + rename.getFunction());
			} else {
				toRename = toRename.replaceAll(rename.getNewName(), rename.getFunction());
			}
		}
		return toRename;
	}
	
	private static String useRenamingCondition(de.tu_bs.cs.isf.taxonomy.model.taxonomy.Renaming renaming, String toRename) {
		for (de.tu_bs.cs.isf.taxonomy.model.taxonomy.Rename rename : renaming.getRenames()) {
			if (rename.getType().equalsIgnoreCase("boolean")) {
				toRename = toRename.replaceAll(rename.getNewName(), "TRUE=" + rename.getFunction());
			} else {
				toRename = toRename.replaceAll(rename.getNewName(), rename.getFunction());
			}
		}
		return toRename;
	}
	
	private static String useRenamingStatement(Renaming renaming, String toRename) {
		for (Rename rename : renaming.getRename()) {
			toRename = toRename.replaceAll(rename.getNewName(), rename.getFunction());
		}
		return toRename;
	}

	public static boolean provePreWithKey(Condition invariant, Condition preCondition, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProvePreWithKey(invariant, preCondition, vars, conds, renaming, uri, 0, true);
		Console.println("Verify Pre -> Invariant");
		return proveWithKey(location, monitor);
	}
	
	public static File createProvePreWithKey(Condition invariant, Condition preCondition, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, int numberFile, boolean override) {
		
		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				programVariablesString += var.getName() + "; ";
			}
		}
		
		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}
		
		IProject thisProject = getProject(uri);
		
		String preString = preCondition.getName();
		String invariantString = invariant.getName();
		
		if (preString == null || preString.length() == 0) {
			preString = "true";
		}
		if (invariantString == null || invariantString.length() == 0) {
			invariantString = "true";
		}
		
		if(renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			preString = useRenamingCondition(renaming, preString);
			invariantString = useRenamingCondition(renaming, invariantString);
		}
		
		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + " Heap heapAtPre;}"
				+ "\\problem {(" + preString + " " + globalConditionsString + ") -> {heapAtPre := heap} (" + invariantString + globalConditionsString +  ")}";

		String location = thisProject.getLocation() + "/src/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = writeFile(problem, location, numberFile, override);
		return keyFile;
	}
	
	public static boolean provePostWithKey(Condition invariant, Condition guard, Condition postCondition, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProvePostWithKey(invariant, guard, postCondition, vars, conds, renaming, uri, 0, true);
		Console.println("Verify (Invariant & !Guard) -> Post");
		return proveWithKey(location, monitor);
	}
	public static File createProvePostWithKey(Condition invariant, Condition guard, Condition postCondition, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, int numberFile, boolean override) {
		
		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				programVariablesString += var.getName() + "; ";
			}
		}
		
		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}
		
		IProject thisProject = getProject(uri);
		
		String postString = postCondition.getName();
		String guardString = guard.getName();
		String invariantString = invariant.getName();
		
		if (postString == null || postString.length() == 0) {
			postString = "true";
		}
		if (guardString == null || guardString.length() == 0) {
			guardString = "true";
		}
		if (invariantString == null || invariantString.length() == 0) {
			invariantString = "true";
		}
		
		if(renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			postString = useRenamingCondition(renaming, postString);
			invariantString = useRenamingCondition(renaming, invariantString);
		}

		
		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + " Heap heapAtPre;}"
				+ "\\problem {(" + invariantString + " & !(" + guardString + ") " + globalConditionsString + ") -> {heapAtPre := heap} (" + postString + globalConditionsString + ")}";

		String location = thisProject.getLocation() + "/src/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = writeFile(problem, location, numberFile, override);
		return keyFile;
	}

	public static boolean provePreSelWithKey(EList<Condition> guards, Condition preCondition, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProvePreSelWithKey(guards, preCondition, vars, conds, renaming, uri, 0, true);
		Console.println("Verify Pre -> GvGvG...");
		return proveWithKey(location, monitor);
	}
	public static File createProvePreSelWithKey(EList<Condition> guards, Condition preCondition, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, int numberFile, boolean override) {
		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				programVariablesString += var.getName() + "; ";
			}
		}
		
		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}
		
		IProject thisProject = getProject(uri);
		
		String preString = preCondition.getName();
		String guardString;
		if (guards != null && guards.get(0) != null) {
			guardString = "((" + guards.get(0).getName() + ")";
			for (int i = 1; i < guards.size(); i++) {
				guardString += " | (" + guards.get(i).getName() + ")";
			}
			guardString += ")";
		} else {
			guardString = "true";
		}
		
		
		if (preString == null || preString.length() == 0) {
			preString = "true";
		}
		
		if(renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			preString = useRenamingCondition(renaming, preString);
			guardString = useRenamingCondition(renaming, guardString);
		}

		
		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + "}"
				+ "\\problem {(" + preString + globalConditionsString + ") -> (" + guardString + globalConditionsString + ")}";

		String location = thisProject.getLocation() + "/src/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = writeFile(problem, location, numberFile, override);
		return keyFile;
	}

	public static boolean proveVariantWithKey(String code, Condition invariant, JavaVariables vars, GlobalConditions conds,
			Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProveVariantWithKey(code, invariant, vars, conds, renaming, uri, 0, true);
		Console.println("Verify Pre -> {WhileStatement} (true)");
		return proveWithKey(location, monitor);
	}
	
	public static File createProveVariantWithKey(String code, Condition invariant, JavaVariables vars, GlobalConditions conds,
			Renaming renaming, URI uri, int numberFile, boolean override) {
		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				programVariablesString += var.getName() + "; ";
			}
		}
		
		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}
		
		IProject thisProject = getProject(uri);
		
		String invariantString = invariant.getName();
		
		if (invariantString == null || invariantString.length() == 0) {
			invariantString = "true";
		}
		
		if(renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			invariantString = useRenamingCondition(renaming, invariantString);
		}

		
		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + " Heap heapAtPre;}"
				+ "\\problem {(" + invariantString + globalConditionsString + ") -> {heapAtPre := heap} \\<{" + code + "}\\> (true)}";
		
		String location = thisProject.getLocation() + "/src/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = writeFile(problem, location, numberFile, override);
		return keyFile;
	}
	
	public static boolean proveVariant2WithKey(String code, Condition invariant, Condition guard, Variant variant, JavaVariables vars, GlobalConditions conds,
			Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProveVariant2WithKey(code, invariant, guard, variant, vars, conds, renaming, uri, 0, true);
		Console.println("Verify Pre -> {WhileStatement} (variant<variant0 & variant >= 0)");
		return proveWithKey(location, monitor);
	}
	
	public static File createProveVariant2WithKey(String code, Condition invariant, Condition guard, Variant variant, JavaVariables vars, GlobalConditions conds,
			Renaming renaming, URI uri, int numberFile, boolean override) {
		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				programVariablesString += var.getName() + "; ";
			}
		}
		programVariablesString += "int variant;";
		
		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}
		
		IProject thisProject = getProject(uri);
		
		String invariantString = invariant.getName();
		
		if (invariantString == null || invariantString.length() == 0) {
			invariantString = "true";
		}
		
		String guardString = guard.getName();
		
		if (guardString == null || guardString.length() == 0) {
			guardString = "true";
		}
		
		if(renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			invariantString = useRenamingCondition(renaming, invariantString);
			guardString = useRenamingCondition(renaming, guardString);
		}
		
		String variantString = variant.getName();
		
		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + " Heap heapAtPre;}"
				+ "\\problem {(" + invariantString + " & " +  guardString + globalConditionsString + ") ->{variant := " + variantString + " || heapAtPre := heap} \\<{" + code + "}\\> (("
				+ variantString + ") <variant & " + variantString +  ">=0"  + globalConditionsString + ")}";
		
		String location = thisProject.getLocation() + "/src/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = writeFile(problem, location, numberFile, override);
		return keyFile;
	}
	
	public static boolean provePreImplPreWithKey(Condition preParent, Condition preChild, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProvePreImplPreWithKey(preParent, preChild, vars, conds, renaming, uri, 0, true);
		Console.println("Verify PreParent -> PreChild");
		return proveWithKey(location, monitor);
	}
	
	public static boolean provePostImplPostWithKey(Condition postParent, Condition postChild, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProvePreImplPreWithKey(postChild, postParent, vars, conds, renaming, uri, 0, true);
		Console.println("Verify PostChild -> PostParent");
		return proveWithKey(location, monitor);
	}
	
	public static File createProvePreImplPreWithKey(Condition preParent, Condition preChild, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, int numberFile, boolean override) {
		
		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				programVariablesString += var.getName() + "; ";
			}
		}
		
		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}
		
		IProject thisProject = getProject(uri);
		
		String preParentString = preParent.getName();
		String preChildString = preChild.getName();
		
		if (preParentString == null || preParentString.length() == 0) {
			preParentString = "true";
		}
		if (preChildString == null || preChildString.length() == 0) {
			preChildString = "true";
		}
		
		if(renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			preParentString = useRenamingCondition(renaming, preParentString);
			preChildString = useRenamingCondition(renaming, preChildString);
		}
		
		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";"
				+ "\\programVariables {" + programVariablesString + " Heap heapAtPre;}"
				+ "\\problem {(" + preParentString + " " + globalConditionsString + ") -> {heapAtPre := heap} (" + preChildString + globalConditionsString + ")}";

		String location = thisProject.getLocation() + "/src/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = writeFile(problem, location, numberFile, override);
		return keyFile;
	}
	
	public static boolean provePreImplPreWithKey(String preParent, String preChild, de.tu_bs.cs.isf.taxonomy.model.taxonomy.JavaVariables vars,
			de.tu_bs.cs.isf.taxonomy.model.taxonomy.GlobalConditions conds, de.tu_bs.cs.isf.taxonomy.model.taxonomy.Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProvePreImplPreWithKey(preParent, preChild, vars, conds, renaming, uri, 0, true);
		Console.println("Verify PreParent -> PreChild");
		return proveWithKey(location, monitor);
	}
	
	public static boolean provePostImplPostWithKey(String postParent, String postChild, de.tu_bs.cs.isf.taxonomy.model.taxonomy.JavaVariables vars,
			de.tu_bs.cs.isf.taxonomy.model.taxonomy.GlobalConditions conds, de.tu_bs.cs.isf.taxonomy.model.taxonomy.Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProvePreImplPreWithKey(postChild, postParent, vars, conds, renaming, uri, 0, true);
		Console.println("Verify PostChild -> PostParent");
		return proveWithKey(location, monitor);
	}
	
	public static File createProvePreImplPreWithKey(String preParent, String preChild, de.tu_bs.cs.isf.taxonomy.model.taxonomy.JavaVariables vars,
			de.tu_bs.cs.isf.taxonomy.model.taxonomy.GlobalConditions conds, de.tu_bs.cs.isf.taxonomy.model.taxonomy.Renaming renaming, URI uri, int numberFile, boolean override) {
		
		String programVariablesString = "";
		if (vars != null) {
			for (de.tu_bs.cs.isf.taxonomy.model.taxonomy.JavaVariable var : vars.getVariables()) {
				programVariablesString += var.getName() + "; ";
			}
		}
		
		String globalConditionsString = "";
		if (conds != null) {
			for (de.tu_bs.cs.isf.taxonomy.model.taxonomy.Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}
		
		IProject thisProject = getProject(uri);
		
		
		if(renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			preParent = useRenamingCondition(renaming, preParent);
			preChild = useRenamingCondition(renaming, preChild);
		}
		
		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";"
				+ "\\programVariables {" + programVariablesString + "}"
				+ "\\problem {(" + preParent + " " + globalConditionsString + ") -> (" + preChild + globalConditionsString + ")}";

		String location = thisProject.getLocation() + "/src/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = writeFile(problem, location, numberFile, override);
		return keyFile;
	}

	public static boolean checkFileIsProved(URI uri, int numberFile) {
		IProject project = getProject(uri);
		File location = new File(project.getLocation() + "/src/prove" + uri.trimFileExtension().lastSegment() + "/prove" + numberFile + ".key");
		if (location.exists()) {
			try {
				KeYEnvironment<?> env = KeYEnvironment.load(location, null, null, null);
				Proof proof = env.getLoadedProof();
				return proof.openGoals().isEmpty();
			} catch (ProblemLoaderException e) {
				e.printStackTrace();
			}
		}
		return false;
		
		
	}
	
	public static boolean proveMethodFormulaWithKey(Condition second, Condition first, List<JavaVariable> vars,
			List<Condition> conds, List<Rename> renaming, URI uri, IProgressMonitor monitor) {
		File location = createProveMethodFormulaWithKey(second, first, vars, conds, renaming, uri, 0, true);
		Console.println("Verify Condition -> Condition");
		return proveWithKey(location, monitor);
	}
	
	public static File createProveMethodFormulaWithKey(Condition second, Condition first, List<JavaVariable> vars,
			List<Condition> conds, List<Rename> renaming, URI uri, int numberFile, boolean override) {
		
		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars) {
				programVariablesString += var.getName() + "; ";
			}
		}
		
		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}
		
		IProject thisProject = getProject(uri);
		
		String firstString = first.getName();
		String secondString = second.getName();
		
		if (firstString == null || firstString.length() == 0) {
			firstString = "true";
		}
		if (secondString == null || secondString.length() == 0) {
			secondString = "true";
		}
		
		if(renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			firstString = useRenamingCondition(renaming, firstString);
			secondString = useRenamingCondition(renaming, secondString);
		}
		
		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + " Heap heapAtPre;}"
				+ "\\problem {(" + firstString + " " + globalConditionsString + ") -> {heapAtPre := heap} (" + secondString + globalConditionsString + ")}";

		String location = thisProject.getLocation() + "/src/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = writeFile(problem, location, numberFile, override);
		return keyFile;
	}

	public static String proveUseWeakestPreWithKey(AbstractStatement statement, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProveUseWeakestPreWithKey(statement, vars, conds, renaming, uri, 0, true);
		Console.println("Construct Pre by using weakest precondition calculus");
		return createWPWithKey(location, monitor);
	}

	private static File createProveUseWeakestPreWithKey(AbstractStatement statement, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, int numberFile, boolean override) {
		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				programVariablesString += var.getName() + "; ";
			}
		}
		
		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}
		
		IProject thisProject = getProject(uri);
		
		String post = statement.getPostCondition().getName();
		String stat = statement.getName();
		
		if (post == null || post.length() == 0) {
			post = "true";
		}
		
		if(renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			post = useRenamingCondition(renaming, post);
			stat = useRenamingStatement(renaming, stat);
		}

		
		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";"
				+ "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + "}"
				+ "\\problem {\\<{" + stat + "}\\> (" + post + ")}";

		String location = thisProject.getLocation() + "/src/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = writeFile(problem, location, numberFile, override);
		return keyFile;
	}
	
	public static String createWPWithKey(File location, IProgressMonitor monitor) {
		Proof proof = createKeyProof(location, monitor);
		if (proof != null) {
			String wp = "";
			Iterator<Goal> it = proof.openGoals().iterator();
			Goal goal = it.next();
			String[] goalString = goal.toString().split("==>");
			String antecedent = goalString[0].trim();
			String succedent = goalString[1].trim();
			wp += "(";
			if (antecedent.isEmpty()) {
				wp += succedent; 
			} else {
				if (succedent.isEmpty()) {
					wp += "!(" + antecedent + ")";
				} else {
					wp += antecedent + " -> " + succedent;
				}
			}
			wp += ")";
			if (it.hasNext()) {
				goal = it.next();
				goalString = goal.toString().split("==>");
				antecedent = goalString[0].trim();
				succedent = goalString[1].trim();
				wp += " & (";
				if (antecedent.isEmpty()) {
					wp += succedent; 
				} else {
					if (succedent.isEmpty()) {
						wp += "!" + antecedent;
					} else {
						wp += antecedent + " -> " + succedent;
					}
				}
				wp += ")";
			}
			Console.println("Weakest precondition is: " + wp);
			return wp;
		}
		return "";
	}

	private static Proof createKeyProof(File location, IProgressMonitor monitor) {
		Proof proof = null;
		List<File> classPaths = null; // Optionally: Additional specifications
										// for API classes
		File bootClassPath = null; // Optionally: Different default
									// specifications for Java API
		List<File> includes = null; // Optionally: Additional includes to
									// consider
		try {
			// Ensure that Taclets are parsed
			if (!ProofSettings.isChoiceSettingInitialised()) {
				KeYEnvironment<?> env = KeYEnvironment.load(location, classPaths, bootClassPath, includes);
				env.dispose();
			}
			// Set Taclet options
			ChoiceSettings choiceSettings = ProofSettings.DEFAULT_SETTINGS.getChoiceSettings();
			HashMap<String, String> oldSettings = choiceSettings.getDefaultChoices();
			HashMap<String, String> newSettings = new HashMap<String, String>(oldSettings);
			newSettings.putAll(MiscTools.getDefaultTacletOptions());
			newSettings.put("runtimeExceptions", "runtimeExceptions:ban");
			choiceSettings.setDefaultChoices(newSettings);
			// Load source code
			KeYEnvironment<?> env = KeYEnvironment.load(location, classPaths, bootClassPath, includes);
			proof = env.getLoadedProof();
			// Set proof strategy options
			StrategyProperties sp = proof.getSettings().getStrategySettings().getActiveStrategyProperties();
			sp.setProperty(StrategyProperties.METHOD_OPTIONS_KEY, StrategyProperties.METHOD_EXPAND);
			sp.setProperty(StrategyProperties.LOOP_OPTIONS_KEY, StrategyProperties.LOOP_EXPAND);
			sp.setProperty(StrategyProperties.DEP_OPTIONS_KEY, StrategyProperties.DEP_ON);
			sp.setProperty(StrategyProperties.QUERY_OPTIONS_KEY, StrategyProperties.QUERY_RESTRICTED);// StrategyProperties.QUERY_ON
			sp.setProperty(StrategyProperties.NON_LIN_ARITH_OPTIONS_KEY, StrategyProperties.NON_LIN_ARITH_DEF_OPS);
			sp.setProperty(StrategyProperties.STOPMODE_OPTIONS_KEY, StrategyProperties.STOPMODE_DEFAULT);
			proof.getSettings().getStrategySettings().setActiveStrategyProperties(sp);
			// Make sure that the new options are used
			int maxSteps = 20000;
			ProofSettings.DEFAULT_SETTINGS.getStrategySettings().setMaxSteps(maxSteps);
			ProofSettings.DEFAULT_SETTINGS.getStrategySettings().setActiveStrategyProperties(sp);
			proof.getSettings().getStrategySettings().setMaxSteps(maxSteps);
			proof.setActiveStrategy(proof.getServices().getProfile().getDefaultStrategyFactory().create(proof, sp));
			// Start auto mode
			Console.println("Start proof: " + location.getName());
			if (monitor != null) {
				env.getUi().getProofControl().startAutoMode(proof);
				while(env.getUi().getProofControl().isInAutoMode()) {
					if (monitor.isCanceled()) {
						env.getUi().getProofControl().stopAndWaitAutoMode();
						Console.println("Proof is canceled.");
					}
				}
			} else {
				env.getUi().getProofControl().startAndWaitForAutoMode(proof);
			}
//			Console.println("Proofcounter: " + ++proofCounter);
			// Show proof result
			try {
				proof.saveToFile(location);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ProblemLoaderException e) {
			Console.println("Exception at '" + e.getCause() + "'");
			e.printStackTrace();
		}
		return proof;
	}
	
	public static void createKeyProofUserstudy(File location, int proofCounter) {
		File keyFile = null;
		List<File> classPaths = null; // Optionally: Additional specifications
										// for API classes
		File bootClassPath = null; // Optionally: Different default
									// specifications for Java API
		List<File> includes = null; // Optionally: Additional includes to
									// consider
		try {
			// Ensure that Taclets are parsed
			if (!ProofSettings.isChoiceSettingInitialised()) {
				KeYEnvironment<?> env = KeYEnvironment.load(location, classPaths, bootClassPath, includes);
				env.dispose();
			}
			// Set Taclet options
			ChoiceSettings choiceSettings = ProofSettings.DEFAULT_SETTINGS.getChoiceSettings();
			HashMap<String, String> oldSettings = choiceSettings.getDefaultChoices();
			HashMap<String, String> newSettings = new HashMap<String, String>(oldSettings);
			newSettings.putAll(MiscTools.getDefaultTacletOptions());
			newSettings.put("runtimeExceptions", "runtimeExceptions:ban");
			choiceSettings.setDefaultChoices(newSettings);
			// Load source code
			KeYEnvironment<?> env = KeYEnvironment.load(location, classPaths, bootClassPath, includes);
			// proof = env.getLoadedProof();
			try {
				// List all specifications of all types in the source location
				// (not classPaths and bootClassPath)
				final List<Contract> proofContracts = new LinkedList<Contract>();
				Set<KeYJavaType> kjts = env.getJavaInfo().getAllKeYJavaTypes();
				for (KeYJavaType type : kjts) {
					if (!KeYTypeUtil.isLibraryClass(type)) {
						ImmutableSet<IObserverFunction> targets = env.getSpecificationRepository().getContractTargets(type);
						for (IObserverFunction target : targets) {
							ImmutableSet<Contract> contracts = env.getSpecificationRepository().getContracts(type, target);
							for (Contract contract : contracts) {
								proofContracts.add(contract);
							}
						}
					}
				}
				// Perform proofs
				Contract contract = proofContracts.get(0);
				Proof proof = null;
				try {
					// Create proof
					proof = env.createProof(contract.createProofObl(env.getInitConfig(), contract));
					// Set proof strategy options
					StrategyProperties sp = proof.getSettings().getStrategySettings().getActiveStrategyProperties();
					sp.setProperty(StrategyProperties.METHOD_OPTIONS_KEY, StrategyProperties.METHOD_EXPAND);
					sp.setProperty(StrategyProperties.LOOP_OPTIONS_KEY, StrategyProperties.LOOP_INVARIANT);
					sp.setProperty(StrategyProperties.DEP_OPTIONS_KEY, StrategyProperties.DEP_ON);
					sp.setProperty(StrategyProperties.QUERY_OPTIONS_KEY, StrategyProperties.QUERY_RESTRICTED);// StrategyProperties.QUERY_ON
					sp.setProperty(StrategyProperties.NON_LIN_ARITH_OPTIONS_KEY, StrategyProperties.NON_LIN_ARITH_DEF_OPS);
					sp.setProperty(StrategyProperties.STOPMODE_OPTIONS_KEY, StrategyProperties.STOPMODE_DEFAULT);
					proof.getSettings().getStrategySettings().setActiveStrategyProperties(sp);
					// Make sure that the new options are used
					int maxSteps = 5000;
					ProofSettings.DEFAULT_SETTINGS.getStrategySettings().setMaxSteps(maxSteps);
					ProofSettings.DEFAULT_SETTINGS.getStrategySettings().setActiveStrategyProperties(sp);
					proof.getSettings().getStrategySettings().setMaxSteps(maxSteps);
					proof.setActiveStrategy(proof.getServices().getProfile().getDefaultStrategyFactory().create(proof, sp));
					// Start auto mode
//						MainWindow.getInstance().setVisible(true);
					env.getUi().getProofControl().startAndWaitForAutoMode(proof);
					// Show proof result
					Console.println("Proof is closed: " + proof.openGoals().isEmpty());
	                try {
	                	String locationWithoutFileEnding = location.toString().substring(0, location.toString().indexOf("."));
	                	keyFile = new File(locationWithoutFileEnding + ".proof");
	    				proof.saveToFile(keyFile);
	    				IWorkspace workspace = ResourcesPlugin.getWorkspace();    
	    				IPath iLocation = Path.fromOSString(keyFile.getAbsolutePath()); 
	    				IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
	    				ifile.refreshLocal(0, null);
	    			} catch (IOException | CoreException e) {
	    				e.printStackTrace();
	    			}
				} catch (ProofInputException e) {
					Console.println(
							"Exception at '" + contract.getDisplayName() + "' of " + contract.getTarget() + ":");
					e.printStackTrace();
				} finally {
					if (proof != null) {
						proof.dispose(); // Ensure always that all instances
											// of Proof are disposed
					}
				}
			} finally {
				env.dispose(); // Ensure always that all instances of
								// KeYEnvironment are disposed
			}
			MainWindow.getInstance().loadProblem(keyFile);
			MainWindow.getInstance().setVisible(true);
		} catch (ProblemLoaderException e) {
			Console.println("Exception at '" + e.getCause() + "'");
			e.printStackTrace();
		}
	}
}
