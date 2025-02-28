package de.tu_bs.cs.isf.cbc.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.exceptions.ClassHandlerException;
import de.tu_bs.cs.isf.cbc.exceptions.ExceptionMessages;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;

public class ClassHandler {
    public static final String GENERATED_CLASSNAME = "GeneratedClass";

    private String className;
    private List<MethodHandler> methods = new ArrayList<MethodHandler>();
    private List<Variable> globalVars = new ArrayList<Variable>();
    private URI projectUri;
    private List<String> projectJavaFiles;
    private List<String> projectInternalClasses;

    public ClassHandler(final String className, final URI projectUri) throws SettingsException {
	this.className = className;
	this.projectUri = projectUri;
	try {
	    setup();
	} catch (ClassHandlerException e) {
	    // Console.println(e.getMessage());
	    e.printStackTrace();
	}
    }

    private List<String> getNewSPLVars(final List<String> oldVars) {
	final List<String> gVars = new ArrayList<String>();
	var vars = TestUtilSPL.getInstance().readFieldsFromClass(className, projectUri.toPlatformString(false));
	for (Field field : vars.getFields()) {
	    if (field.getName() == null || field.getType() == null) {
		continue;
	    }
	    if (field.isIsStatic()) {
		if (oldVars.contains("static " + field.getType() + " " + field.getName())) {
		    continue;
		}
		gVars.add("static " + field.getType() + " " + field.getName());
	    } else {
		if (oldVars.contains(field.getType() + " " + field.getName())) {
		    continue;
		}
		gVars.add(field.getType() + " " + field.getName());
	    }
	}
	return gVars;
    }

    public void setup() throws SettingsException, ClassHandlerException {
	String classCode;
	final List<String> gVars;
	final List<String> featuregVars = new ArrayList<String>();
	if (this.projectUri == null) {
	    genEmptyConstructor();
	}
	// find out how many fields the class *className* has
	if (className.equals(GENERATED_CLASSNAME)) {
	    gVars = new ArrayList<String>();
	} else if (isExternalClass(className)) {
	    classCode = classExists(this.projectUri, className);
	    if (classCode.isEmpty()) {
		return;
	    }
	    gVars = getGvarsOfExternalClass(classCode);
	} else {
	    gVars = getGvarsOfCbCClass(this.projectUri, className);
	    if (FileHandler.instance.isSPL(projectUri)) {
		featuregVars.addAll(gVars);
		gVars.addAll(getNewSPLVars(gVars));
	    }
	}
	if (gVars == null) {
	    throw new ClassHandlerException(ExceptionMessages.gVarsOfClass(className));
	}
	// generate global vars and fields
	for (int i = 0; i < gVars.size(); i++) {
	    Variable v;
	    if (gVars.get(i).contains("static")) {
		gVars.set(i, gVars.get(i).replaceAll("static\\s", "")); // ;-)
		v = new Variable("static", gVars.get(i).split("\\s")[0], gVars.get(i).split("\\s")[1]);
	    } else {
		v = new Variable(gVars.get(i).split("\\s")[0], gVars.get(i).split("\\s")[1]);
	    }
	    this.addGlobalVar(v);
	}
	// generate constructor
	this.addMethod(className, generateConstructor(className, gVars));
	// generate constructor for feature if present
	if (FileHandler.instance.isSPL(projectUri) && featuregVars.hashCode() != gVars.hashCode()) {
	    this.addMethod(className, generateConstructor(className, featuregVars));
	}
	// also generate the default constructor if the last constructor contains params
	if (gVars.size() > 0) {
	    genEmptyConstructor();
	}
    }

    public boolean isClass(String className) {
	if (getProjectInternalClasses().contains(className)) {
	    return true;
	}
	if (getProjectJavaFiles().contains(className)) {
	    return true;
	}
	return false;
    }

    private void loadInternalClasses() {
	var project = FileUtil.getProject(this.projectUri);
	var cbcClasses = FileUtil.getCbCClasses(project);
	for (var cbcClass : cbcClasses) {
	    var modelClass = (ModelClass) cbcClass.getContents().get(0);
	    var name = modelClass.getName();
	    this.projectInternalClasses.add(name);
	}
    }

    private boolean loadAllJavaFilesFromProject() {
	var projectLocation = FileUtil.getProjectLocation(this.projectUri);
	var project = FileUtil.getProject(this.projectUri);
	var javaFiles = FileUtil.getFilesFromProject(project, ".java");
	File f;
	java.nio.file.Path p;
	java.nio.file.Path source;

	for (var javaFile : javaFiles) {
	    this.projectJavaFiles.add(javaFile.getName().substring(0, javaFile.getName().indexOf('.')));
	    // copy file into tests folder
	    p = java.nio.file.Paths.get(projectLocation + "/tests/" + javaFile.getName());
	    source = Paths.get(projectLocation + "/" + javaFile.getProjectRelativePath().toOSString());
	    try {
		f = new File(p.toString());
		if (!f.exists()) {
		    String code = Files.readString(source);
		    code = CodeHandler.removeAllComments(code);
		    f.createNewFile();
		    Files.write(p, code.getBytes(), StandardOpenOption.WRITE);
		}
	    } catch (IOException e) {
		e.printStackTrace();
		return false;
	    }
	}
	return true;
    }

    private List<String> getProjectJavaFiles() {
	if (this.projectJavaFiles == null) {
	    this.projectJavaFiles = new ArrayList<String>();
	    loadAllJavaFilesFromProject();
	}
	return this.projectJavaFiles;
    }

    private List<String> getProjectInternalClasses() {
	if (this.projectInternalClasses == null) {
	    this.projectInternalClasses = new ArrayList<String>();
	    loadInternalClasses();
	}
	return this.projectInternalClasses;
    }

    private boolean isExternalClass(String className) {
	if (getProjectInternalClasses().contains(className)) {
	    return false;
	}
	if (!getProjectJavaFiles().contains(className)) {
	    return false;
	}
	return true;
    }

    public static String classExists(final URI projectUri, String className) {
	var dir = new File(FileUtil.getProjectLocation(projectUri) + "\\tests");
	if (!dir.exists()) {
	    return "";
	}
	var javaFile = new File(FileUtil.getProjectLocation(projectUri) + "\\tests\\" + className + ".java");
	if (!javaFile.exists()) {
	    return "";
	}
	try {
	    var code = Files.readString(Paths.get(javaFile.getPath()));
	    return CodeHandler.removeAllComments(code);
	} catch (IOException e) {
	}
	return "";
    }

    private List<String> getGvarsOfExternalClass(String code) {
	final var output = new ArrayList<String>();
	final var cleanedOutput = new ArrayList<String>();
	code = CodeHandler.removeAllComments(code);
	if (code.isEmpty()) {
	    return null;
	}
	var firstPart = code.substring(0, code.indexOf('{'));
	var secondPart = code.substring(code.indexOf('{') + 1, code.length());
	secondPart = secondPart.trim();
	code = firstPart + "{\n" + secondPart;
	code = code.replaceAll("\r", "");
	code = code.replaceAll("\t", "");
	code = code.substring(code.indexOf('{') + 1, code.length()).trim();

	// TODO: this part seems to extract global vars correctly, but needs to be
	// thoroughly tested.
	var end = CodeHandler.findClosingBracketIndex(code, code.indexOf('{'), '{');
	while (code.charAt(end + 1) == ';') {
	    var helper = code.substring(end + 1);
	    end = CodeHandler.findClosingBracketIndex(code, code.indexOf('{'), '{');
	}
	var firstMethod = CodeHandler.getCurrentBlock(code, end - 1);
	code = code.substring(0, code.indexOf(firstMethod)).trim();
	code = code.replaceAll("\r", "");
	code = code.replaceAll("\t", "");
	code = code.replaceAll("\n", "");
	// -

	if (!code.contains(";")) {
	    return output;
	}
	output.addAll(Arrays.asList(code.split(";")));
	for (var v : output) {
	    v = v.replaceAll("\\sstatic", "");
	    v = v.replaceAll("\\sfinal", "");
	    if (v.contains("=")) {
		v = v.substring(0, v.indexOf('='));
		v = v.trim();
		var splitter = v.split("\\s");
		v = splitter[splitter.length - 2] + " " + splitter[splitter.length - 1];
	    } else {
		var splitter = v.split("\\s");
		v = splitter[splitter.length - 2] + " " + splitter[splitter.length - 1];
	    }
	    cleanedOutput.add(v);
	}
	// cleanedOutput.add(code);
	return cleanedOutput;
    }

    public static List<Variable> getGvarsOfCbCClassAsVar(final URI projectUri, String className)
	    throws SettingsException {
	final List<Variable> globalVars = new ArrayList<Variable>();
	Collection<Resource> resources = FileUtil.getCbCClasses(FileUtil.getProject(projectUri));
	for (Resource resource : resources) {
	    for (EObject object : resource.getContents()) {
		if (object instanceof ModelClass) {
		    ModelClass modelClass = (ModelClass) object;
		    if (modelClass.getName().equals(className)) {
			var fields = modelClass.getFields();
			for (Field field : fields) {
			    if (field.getName() == null || field.getType() == null) {
				continue;
			    }
			    Variable v = new Variable(field.getType(), field.getName());
			    globalVars.add(v);
			}
			return globalVars;
		    }
		}
	    }
	}
	return globalVars;
    }

    public static List<String> getGvarsOfCbCClass(final URI projectUri, String className) {
	final List<String> globalVars = new ArrayList<String>();
	Collection<Resource> resources = FileUtil.getCbCClasses(FileUtil.getProject(projectUri));
	for (Resource resource : resources) {
	    for (EObject object : resource.getContents()) {
		if (object instanceof ModelClass) {
		    ModelClass modelClass = (ModelClass) object;
		    if (modelClass.getName().equals(className)) {
			var fields = modelClass.getFields();
			for (Field field : fields) {
			    if (field.getName() == null || field.getType() == null) {
				continue;
			    }
			    if (field.isIsStatic()) {
				globalVars.add("static " + field.getType() + " " + field.getName());
			    } else {
				globalVars.add(field.getType() + " " + field.getName());
			    }
			}
			return globalVars;
		    }
		}
	    }
	}
	return null;
    }

    public static EList<Field> getFields(final URI projectUri) {
	final List<String> globalVars = new ArrayList<String>();
	Collection<Resource> resources = FileUtil.getCbCClasses(FileUtil.getProject(projectUri));
	for (Resource resource : resources) {
	    for (EObject object : resource.getContents()) {
		if (object instanceof ModelClass) {
		    ModelClass modelClass = (ModelClass) object;
		    for (int i = 0; i < modelClass.getFields().size(); i++) {
			modelClass.getFields().get(i).setName(modelClass.getFields().get(i).getType() + " "
				+ modelClass.getFields().get(i).getName());
		    }
		    return modelClass.getFields();
		}
	    }
	}
	return null;
    }

    private String generateConstructor(final String className, final List<String> gVars) {
	final StringBuffer code = new StringBuffer();
	String globalVarName;

	code.append("\n\t" + "public " + className + "(");
	int counter = 0;
	for (var g : gVars) {
	    if (counter != gVars.size() - 1) {
		code.append(g + ", ");
	    } else {
		code.append(g);
	    }
	    counter++;
	}
	code.append(") {\n");

	for (var g : gVars) {
	    var splitter = g.split("\\s");
	    if (splitter.length > 1) {
		globalVarName = g.split("\\s")[splitter.length - 1];
	    } else {
		globalVarName = g.split("\\s")[0];
	    }
	    code.append("\t\t" + "this." + globalVarName + " = " + globalVarName + ";\n");
	}
	code.append("\t}\n\n");
	return code.toString().trim();
    }

    private void genEmptyConstructor() {
	if (this.containsMethod("public " + className + "()"))
	    return;
	this.addMethod(className, "public " + className + "(){}");
    }

    public String getCode() {
	return getCode(true);
    }

    public String getCode(boolean genMethods) {
	String code = "public class " + this.className + " {\n";
	for (var gVar : globalVars) {
	    if (gVar.getValue() != null) {
		code += "public " + (!gVar.getModifier().isEmpty() ? gVar.getModifier() + " " : "") + gVar.getType()
			+ " " + gVar.getName() + " = " + gVar.getValue() + ";\n";
	    } else {
		code += "public " + (!gVar.getModifier().isEmpty() ? gVar.getModifier() + " " : "") + gVar.getType()
			+ " " + gVar.getName() + ";\n";
	    }
	}
	if (genMethods) {
	    code += "\n";
	    for (var method : methods) {
		code += method.getCode() + "\n";
	    }
	}
	code += "}";
	code = CodeHandler.indentCode(code, 0);
	return code;
    }

    public void addGlobalVar(final Variable variable) {
	globalVars.add(variable);
    }

    public boolean addMethod(final String signature, final String innerCode) {
	if (containsMethod(signature)) {
	    return false;
	}
	methods.add(new MethodHandler(signature, innerCode));
	return true;
    }

    public boolean addMethod(final MethodHandler method) {
	return addMethod(method.getSignature(), method.getInnerCode());
    }

    public MethodHandler getMethod(final String signature) {
	for (var m : methods) {
	    if (m.getSignature().equals(signature)) {
		return m;
	    }
	}
	return null;
    }

    public String getMethodSigByName(final String methodName) {
	for (var method : methods) {
	    if (method.getMethodName().equals(methodName)) {
		return method.getSignature();
	    }
	}
	return null;
    }

    public String getClassName() {
	return this.className;
    }

    public boolean removeMethod(final String signatureToRemove) {
	boolean found = false;

	for (int i = 0; i < methods.size(); i++) {
	    if (methods.get(i).getSignature().equals(signatureToRemove)) {
		methods.remove(i);
		found = true;
	    }
	}
	return found;
    }

    public boolean containsMethod(final MethodHandler method) {
	return containsMethod(method.getSignature());
    }

    public boolean containsMethod(String methodSig) {
	for (final var method : methods) {
	    if (method.getSignature().equals(methodSig)) {
		return true;
	    }
	}
	return false;
    }

    public List<MethodHandler> getAllMethods() {
	return methods;
    }

    public void addMethods(ArrayList<MethodHandler> newMethods) {
	for (final var method : newMethods) {
	    if (!containsMethod(method)) {
		addMethod(method);
	    }
	}
    }

    public List<Variable> getFields() {
	return this.globalVars;
    }

    public static String getClassName(final CbCFormula formula) {
	final String className;
	if (formula.getClassName().isEmpty()) {
	    className = GENERATED_CLASSNAME;
	} else {
	    className = formula.getClassName();
	}
	return className;
    }

    public static ClassHandler getClassByName(final List<ClassHandler> classes, final String className) {
	for (int i = 0; i < classes.size(); i++) {
	    if (classes.get(i).getClassName().equals(className)) {
		return classes.get(i);
	    }
	}
	return null;
    }

    public static String getClassNameFromCode(String code) {
	code = code.substring(code.indexOf("class ") + "class ".length(), code.length());
	return code.substring(0, code.indexOf("{")).trim();
    }

    public static String getImportsStr() {
	final var code = new StringBuffer();
	code.append("import org.testng.ITestContext;\n");
	code.append("import org.testng.Assert;\n");
	code.append("import org.testng.annotations.Test;\n");
	code.append("import java.util.Arrays;\n");
	code.append("import java.util.stream.IntStream;\n");
	code.append("import java.util.function.Supplier;\n\n");
	return code.toString();
    }
}
