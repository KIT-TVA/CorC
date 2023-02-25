package diagnostics;

public class Vertex {
	protected String name;
	protected Vertex next;

	public Vertex(final String name) {
		this.name = name;
		this.next = null;
	}
	
	public String getName() {
		return this.name;
	} 
	
	public Vertex getNext() {
		return this.next;
	}
}
