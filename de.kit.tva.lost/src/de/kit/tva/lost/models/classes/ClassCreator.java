package de.kit.tva.lost.models.classes;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;

public class ClassCreator {
	private ModelClass modelClass;

	public ClassCreator(URI resourceUri) throws SettingsException {
		/*
		 * var basePath =
		 * ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString(); URI
		 * resolvedURI = URI.createDeviceURI(basePath + resourceUri.path());
		 */
		var resolvedURI = resourceUri;
		if (!load(resolvedURI)) {
			create(resolvedURI);
		}
	}

	public ModelClass get() {
		return this.modelClass;
	}

	public List<Field> getFields() {
		/*
		 * var copyFields = new ArrayList<Field>(); for (var field :
		 * modelClass.getFields()) { copyFields.add(EcoreUtil.copy(field)); } return
		 * copyFields;
		 */
		return modelClass.getFields();
	}

	public Method getMethod(String name) throws IOException {
		for (var method : modelClass.getMethods()) {
			if (!method.getName().equals(name))
				continue;
			return method;
		}
		var newMethod = CbcclassFactory.eINSTANCE.createMethod();
		newMethod.setName(name);
		// newMethod.setSignature();
		newMethod.setParentClass(modelClass);
		modelClass.getMethods().add(newMethod);
		modelClass.eResource().save(Collections.EMPTY_MAP);
		return newMethod;
	}

	public void setFields(List<Field> newFields) throws IOException {
		modelClass.getFields().clear();
		modelClass.getFields().addAll(newFields);
		this.modelClass.eResource().save(Collections.EMPTY_MAP);
	}

	public void setParams(Method method, List<Parameter> newParams) throws IOException {
		method.getParameters().clear();
		method.getParameters().addAll(newParams);
		this.modelClass.eResource().save(Collections.EMPTY_MAP);
	}

	private boolean load(URI resourceUri) throws SettingsException {
		var classLoader = new ClassLoader(resourceUri);
		if (classLoader.exists()) {
			modelClass = classLoader.get();
			return true;
		}
		return false;
	}

	private void create(URI resourceUri) {
		modelClass = CbcclassFactory.eINSTANCE.createModelClass();
		// modelClass.setName();
		// TODO

	}

}
