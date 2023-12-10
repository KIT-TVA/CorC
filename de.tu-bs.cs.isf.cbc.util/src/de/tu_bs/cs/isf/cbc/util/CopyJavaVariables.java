package de.tu_bs.cs.isf.cbc.util;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;

public class CopyJavaVariables {
	JavaVariables newVars;
	
	public CopyJavaVariables(final JavaVariables vars) {
		newVars = CbcmodelFactory.eINSTANCE.createJavaVariables();
		copy(vars);
	}
	
	public JavaVariables get() {
		return this.newVars;
	}
	
	private JavaVariables copy(final JavaVariables vars) {
		for (JavaVariable v : vars.getVariables()) {
			JavaVariable newV = CbcmodelFactory.eINSTANCE.createJavaVariable();
			newV.eSet(CbcmodelPackage.eINSTANCE.getJavaVariable_Name(), v.getName());
			newV.eSet(CbcmodelPackage.eINSTANCE.getJavaVariable_Kind(), v.getKind());
			newVars.getVariables().add(newV);
		}
		for (Parameter p : vars.getParams()) {
			Parameter newP = CbcclassFactory.eINSTANCE.createParameter();
			newP.eSet(CbcclassPackage.eINSTANCE.getParameter_Name(), p.getName());
			newP.eSet(CbcclassPackage.eINSTANCE.getParameter_Type(), p.getType());
			newVars.getParams().add(newP);
		}
		for (Field f : vars.getFields()) {
			Field newF = CbcclassFactory.eINSTANCE.createField();
			newF.eSet(CbcclassPackage.eINSTANCE.getField_Name(), f.getName());
			newF.eSet(CbcclassPackage.eINSTANCE.getField_Type(), f.getType());
			newF.eSet(CbcclassPackage.eINSTANCE.getField_Visibility(), f.getVisibility());
			newVars.getFields().add(newF);
		}
		return newVars;
	}
}
