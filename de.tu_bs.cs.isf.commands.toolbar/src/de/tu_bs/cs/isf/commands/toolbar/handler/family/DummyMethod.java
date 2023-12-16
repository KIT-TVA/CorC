package de.tu_bs.cs.isf.commands.toolbar.handler.family;

import org.eclipse.core.resources.IProject;

import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.tool.helper.ClassHandler;
import de.tu_bs.cs.isf.cbc.tool.helper.MethodHandler;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

public class DummyMethod {
	private Method method;
	
	public static DummyMethod createFromSrc(IProject project, String name) throws Exception {
		var method = CbcclassFactory.eINSTANCE.createMethod();
		var files = FileUtil.getFiles(project, ".java");
		for (var file : files) {
			var content = new String(file.getContents(true).readAllBytes());
			var sig = MethodHandler.getMethodSignature(content, name);
			if (!MethodHandler.methodExists(content, name).isEmpty()) {
				var className = ClassHandler.getClassNameFromCode(content);
				var parent = CbcclassFactory.eINSTANCE.createModelClass();
				parent.setName(className);
				method.setParentClass(parent);
				var contract = MethodHandler.getContractFromCode(content, sig);
				return new DummyMethod(createMethodContract(method, contract));
			}
		}
		throw new MetaClassException("Meta method with name '" + name + "' could not be found.");
	}
	
	private DummyMethod(Method method) {
		this.method = method;
	}
	
	public Method get() {
		return this.method;
	}
	
	private static Method createMethodContract(Method method, String contract) {
		initMethod(method);
		var preCon = contract.substring(contract.indexOf("requires") + "requires".length(), contract.length());
		preCon = preCon.substring(0, preCon.indexOf(";")).trim();
		var postCon = contract.substring(contract.indexOf("ensures") + "ensures".length(), contract.length());
		postCon = postCon.substring(0, postCon.indexOf(";")).trim();
		method.getCbcStartTriple().getStatement().getPreCondition().setName(preCon);
		method.getCbcStartTriple().getStatement().getPostCondition().setName(postCon);
		return method;
	}
	
	private static void initMethod(Method method) {
		CbCFormula formula = CbcmodelFactory.eINSTANCE.createCbCFormula();
		AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement.setName("statement");
		formula.setStatement(statement);
		Condition preCondition = CbcmodelFactory.eINSTANCE.createCondition();
		preCondition.setName("pre");
		statement.setPreCondition(preCondition);
		Condition postCondition = CbcmodelFactory.eINSTANCE.createCondition();
		postCondition.setName("post");
		statement.setPostCondition(postCondition);
		method.setCbcStartTriple(formula);
	}
}
