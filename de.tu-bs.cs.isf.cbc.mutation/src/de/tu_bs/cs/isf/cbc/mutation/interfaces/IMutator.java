package de.tu_bs.cs.isf.cbc.mutation.interfaces;
import java.io.File;

public interface IMutator {
	public IMutationOperators ops = null;
	
	public File[] generateMutants(File[] files);
}
