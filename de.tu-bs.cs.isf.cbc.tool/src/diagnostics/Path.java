package diagnostics;

public class Path {
	private static final String EDGE_SYMBOL = "->";
	private Vertex head;
	private float time;
	private int numNodes;
	
	public Path(final TimedObject tObj) {
		this.time = tObj.getExecutionTime();
		buildPath(tObj.getName());
		this.numNodes = countNodes(tObj.getName());
	}
	
	public float getTime() {
		return this.time;
	}
	
	public Vertex nextVertex() {
		if (head == null) {
			return null;
		}
		Vertex cur = head;
		head = head.next;
		return cur;
	}
	
	private int countNodes(String path) {
		if (path == null) {
			return -1;
		}
		int count = 0;
		if (path.length() > 0) {
			count++;
		}
		while (path.contains(EDGE_SYMBOL)) {
			count++;
			path = path.substring(path.indexOf(EDGE_SYMBOL) + EDGE_SYMBOL.length(), path.length());
		}
		return count;
	}
	
	private void buildPath(String pathStr) {
		Vertex cur = head;
		while (pathStr.contains(EDGE_SYMBOL)) {
			final var vertexName = pathStr.substring(0, pathStr.indexOf(EDGE_SYMBOL)).trim();
			if (cur == null) {
				cur = new Vertex(vertexName);
			} else {
				cur.next = new Vertex(vertexName);
			}
			pathStr = pathStr.substring(pathStr.indexOf(EDGE_SYMBOL) + EDGE_SYMBOL.length(), pathStr.length());
			cur = cur.next;
		}
		if (cur == null) {
			cur = new Vertex(pathStr.trim());
		} else {
			cur.next = new Vertex(pathStr.trim());
		}
	}

	public int getNumNodes() {
		return numNodes;
	}

	public Vertex getVertex(int h) {
		var cur = head;
		while(h > 0 && cur.next != null) {
			cur = cur.next;
			h--;
		}
		return cur;
	}
}
