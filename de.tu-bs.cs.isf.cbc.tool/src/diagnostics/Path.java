package diagnostics;

public class Path {
	private String name;
	private float executionTime;

	public Path(final String name) {
		this.name = name;
	}
	
	public Path(final String name, final float executionTime) {
		this.name = name;
		this.executionTime = executionTime;
	}
	
	public String getName() {
		return this.name;
	}
	
	public float getExecutionTime() {
		return this.executionTime;
	}
}
