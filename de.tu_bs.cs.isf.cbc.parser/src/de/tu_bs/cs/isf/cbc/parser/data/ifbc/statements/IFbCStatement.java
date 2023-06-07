package de.tu_bs.cs.isf.cbc.parser.data.ifbc.statements;

import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.cbc.parser.data.Method;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.TypeableResult;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCReferenceEntity;
import de.tu_bs.cs.isf.cbc.parser.exceptions.IFbCException;
import de.tu_bs.cs.isf.lattice.Lattice;

public interface IFbCStatement {
	
	public TypeableResult isTypeable(final Lattice lattice, 
	                                 final String projectName, 
	                                 final Map<String, List<IFbCReferenceEntity>> elevatedEntities, 
	                                 final Map<String, String> changedTypes,
	                                 final List<String> usedCapsules,
	                                 final String optionalGuardSL,
	                                 final Method constructingMethod) throws IFbCException;
	
	public StatementType getType();	
	
	public String getStatement();
	
	public enum StatementType {
		Assignment,
		MethodCall,
		Return,
		Reference;
	}
}
