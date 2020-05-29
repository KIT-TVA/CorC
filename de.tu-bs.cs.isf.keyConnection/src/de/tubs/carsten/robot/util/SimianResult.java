package de.tubs.carsten.robot.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SimianResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2170260634130292982L;
	
	private Map<String, SimianResultLine> lines = new HashMap<>();
	private Set<String> ignoreList = new HashSet<>();

	public SimianResult(String... ignore) {
		ignoreList.addAll(Arrays.asList(ignore));
	}

	public void addLine(String methodName, int numberOfNodes, int time, boolean closed) {
		if (!ignoreList.contains(methodName)) {
			SimianResultLine srl = new SimianResultLine(methodName, numberOfNodes, time, closed);
			lines.put(methodName, srl);
		}
	}

	public int getOverallNumberOfNodes() {
		int numberOfNodes = 0;
		for (SimianResultLine line : lines.values()) {
			numberOfNodes += line.numberOfNodes;
		}
		return numberOfNodes;
	}

	public int getNumberOfNodes(String method) {
		if (lines.containsKey(method)) {
			return lines.get(method).numberOfNodes;
		} else {
			throw new IllegalArgumentException(method + " is unknown");
		}
	}
	
	public int getNumberOfLines(){
		return lines.size();
	}
	
	public boolean isClosed(){
		boolean closed = true;
		for (Iterator<SimianResultLine> iterator = lines.values().iterator(); iterator.hasNext() && closed;) {
			SimianResultLine srl = iterator.next();
			closed &= srl.closed;
		}
		return closed;
	}

	private static class SimianResultLine implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1425059300967658158L;
		@SuppressWarnings("unused")
		String methodName;
		boolean closed;
		int numberOfNodes;
		@SuppressWarnings("unused")
		int time;

		public SimianResultLine(String methodName, int numberOfNodes, int time, boolean closed) {
			super();
			this.methodName = methodName;
			this.numberOfNodes = numberOfNodes;
			this.time = time;
			this.closed = closed;
		}
	}
}
