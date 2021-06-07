package de.tu_bs.cs.isf.cbc.tool.model;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility;

//TODO

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodSignature;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.util.Parser;

public class CbcClassModelUtil {
	
	
	public static void createCbcClassModel(Diagram d, String name, MethodSignature signature, CbCFormula startingTriple, JavaVariables vars) {
		// Initialize the Model
				CbcclassPackage.eINSTANCE.eClass();
				// Register Resource Factory for respective Model
				Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
				Map<String, Object> m = reg.getExtensionToFactoryMap();
				m.put("cbcclass", new XMIResourceFactoryImpl());


				ResourceSet rs = new ResourceSetImpl();
				URI path = d.eResource().getURI().trimSegments(1);
				path = path.appendSegment(name + ".cbcclass");
				// Create Resource and load respective Model Instance
				Resource r = rs
						.createResource(path);
				ModelClass modelClass =  CbcclassFactory.eINSTANCE.createModelClass();
				modelClass.setName(name);
				r.getContents().add(modelClass);
				
				Method method = CbcclassFactory.eINSTANCE.createMethod();
				//method.setCbcDiagram(d.eResource().getURI());
				method.setSignature(signature.getMethodSignature());

				Condition pre = CbcmodelFactory.eINSTANCE.createCondition();
				pre.setName(Parser.getConditionFromCondition(startingTriple.getStatement().getPreCondition().getName()));
				method.setPrecondition(pre);
				
				Condition post = CbcmodelFactory.eINSTANCE.createCondition();
				post.setName(Parser.getConditionFromCondition(startingTriple.getStatement().getPostCondition().getName()));
				method.setPostcondition(post);
				
				method.setAssignable(Parser.getModifieableVarsFromCondition(startingTriple.getStatement().getPostCondition().getName()));
				EList<Method> methods = new BasicEList<Method>();
				methods.add(method);
				
				EList<Field> fields = new BasicEList<Field>();
				for(JavaVariable var: vars.getVariables()) {
					if(var.getKind().equals(VariableKind.GLOBAL)) {
						Field f = CbcclassFactory.eINSTANCE.createField();
						String[] split = var.getName().split(" ");
						String type = split[0];
						String varname = split[1];
						f.setName(varname);
						f.setType(type);
						f.setVisibility(Visibility.PRIVATE); //was sollte hier default sein?
						
						fields.add(f);
					}
				}
				modelClass.eSet(CbcclassPackage.eINSTANCE.getModelClass_Fields(), fields);
				modelClass.eSet(CbcclassPackage.eINSTANCE.getModelClass_Methods(), methods);
				//TODO: Java-File erstellen und URI ergänzen
				r.getContents().add(modelClass);
				try {
					r.save(Collections.EMPTY_MAP);
				} catch (IOException e) {
					e.printStackTrace();
				}
	}

}
