package de.tu_bs.cs.isf.cbc.util;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassOrInterfaceCollector extends VoidVisitorAdapter<Void> {
    private ClassOrInterfaceDeclaration classOrInterfaceDeclaration;
    private ArrayList<FieldDeclaration> fields;
    private ArrayList<MethodDeclaration> methods;
    
    public ClassOrInterfaceCollector() {
    	this.classOrInterfaceDeclaration = new ClassOrInterfaceDeclaration();
    	this.fields = new ArrayList<>();
    	this.methods = new ArrayList<>();
    }
    
    @Override
    public void visit(ClassOrInterfaceDeclaration classIntDec, Void arg) {
    	super.visit(classIntDec, arg);
    	
    	this.classOrInterfaceDeclaration = classIntDec;
    }
    
    @Override
    public void visit(FieldDeclaration field, Void args) {
    	super.visit(field, args);
    	
    	if (!this.fields.contains(field)) {
    		this.fields.add(field);
    	}
    }
    
    @Override
    public void visit(MethodDeclaration method, Void args) {
    	super.visit(method, args);
    	
    	if (!this.methods.contains(method)) {
    		this.methods.add(method);
    	}
    }
    
    public ClassOrInterfaceDeclaration  getClassOrInterfaceDeclaration() {
    	return this.classOrInterfaceDeclaration;
    }
    
    public ArrayList<FieldDeclaration> getFields() {
    	return this.fields;
    }
    
    public ArrayList<MethodDeclaration> getMethods() {
    	return this.methods;
    }
}
