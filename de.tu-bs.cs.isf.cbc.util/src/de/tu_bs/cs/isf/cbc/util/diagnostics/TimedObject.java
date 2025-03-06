package de.tu_bs.cs.isf.cbc.util.diagnostics;

public class TimedObject {
	private String name;
	private float executionTime;

	public TimedObject(final String name) {
		this.name = name;
	}

	public TimedObject(final String name, final float executionTime) {
		this.name = name;
		this.executionTime = executionTime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public float getExecutionTime() {
		return this.executionTime;
	}
}
