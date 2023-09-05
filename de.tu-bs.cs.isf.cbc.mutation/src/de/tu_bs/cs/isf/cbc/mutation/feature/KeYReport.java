package de.tu_bs.cs.isf.cbc.mutation.feature;
import java.util.Map;

import de.tu_bs.cs.isf.cbc.mutation.interfaces.IReport;

import java.util.HashMap;

public class KeYReport implements IReport {

	public double mutationScore = 0;
	public int mutationCount = 0;
	public int killedMutants = 0;
	public Map<String, String> mutantMap = new HashMap<String, String>();
	public Map<String, String> exceptionMap = new HashMap<String, String>();
	//timing in millis, because of KeY Proof.Statistics
	public Map<String, Long> timingMap = new HashMap<String, Long>();
	public Map<String, Integer> notVerifiedMap = new HashMap<String, Integer>();
	//nanos
	public long preProcessingTime = Framework.preProcTime;
	public long postProcessingTime = Framework.postProcTime;
	
	@Override
	public String print() {
		if(mutationCount == 0) return "Zero Mutations checked";
		mutationScore = (double) killedMutants / (double) mutationCount;
		StringBuilder builder = new StringBuilder();
		builder.append("Mutation Score: " + mutationScore + 
				" | Killed Mutants: " + killedMutants + 
				" | Mutation Count: " + mutationCount);
		//Verified Mutants print
		builder.append(System.lineSeparator());
		builder.append("Verified Mutants:" + System.lineSeparator());
		for(String key: mutantMap.keySet()) {
			String method = mutantMap.get(key);
			String muOp = key.split("_")[0];
			builder.append(method + " : " + key + System.lineSeparator());
		}
		//Excpetional Mutants print
		builder.append(System.lineSeparator());
		builder.append("Exceptional Mutants:" + System.lineSeparator());
		for(String key: exceptionMap.keySet()) {
			String exception = exceptionMap.get(key);
			builder.append(key + " : " + exception + System.lineSeparator());
		}
		//Timer per Mutant print
		builder.append(System.lineSeparator());
		builder.append("Timing per Mutant:" + System.lineSeparator());
		for(String key: timingMap.keySet()) {
			Long timing = timingMap.get(key);
			builder.append(key + " : " + timing + System.lineSeparator());
		}
		//Count for Mutation Operators
		builder.append(System.lineSeparator());
		builder.append("Count for Mutation Operators: " + System.lineSeparator());
		for(String key: notVerifiedMap.keySet()) {
			builder.append(key + " : " + notVerifiedMap.get(key) + System.lineSeparator());
		}
		//Timer for Pre- and PostProcessing
		builder.append(System.lineSeparator());
		builder.append("Pre: " + preProcessingTime);
		builder.append(System.lineSeparator());
		builder.append("Post: " + postProcessingTime);

//		System.out.println(builder.toString());
		return builder.toString();
	}

}
