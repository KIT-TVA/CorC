package de.tu_bs.cs.isf.cbc.mutation.interfaces;

public interface IReport {
	public double mutationScore = 0;
	public int mutationCount = 0;
	public int killedMutants = 0;
	
	public String print();
	
}
