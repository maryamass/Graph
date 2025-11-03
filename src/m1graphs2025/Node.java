package m1graphs2025;

import java.util.List;
import java.util.Objects;

public final class Node implements Comparable<Node> {
    private final int id;
    private final Graph graph;
    private final String name;

    public Node(int id, Graph graph) { this(id, null, graph); }
    public Node(int id, String name, Graph graph) {
        if (graph == null) throw new IllegalArgumentException("graph cannot be null");
        this.id = id; this.name = name; this.graph = graph;
    }

    public int getId() { return id; }
    public Graph getGraph() { return graph; }
    public String getName() { return name; }

    public List<Node> getSuccessors() { return graph.getSuccessors(this); }
    public List<Node> getSuccessorsMulti() { return graph.getSuccessorsMulti(this); }
    public boolean adjacent(Node u) { return graph.adjacent(this, u); }
    public int inDegree() { return graph.inDegree(this.getId()); }
    public int outDegree() { return graph.outDegree(this.getId()); }
    public int degree() { return graph.degree(this.getId()); }
    public List<Edge> getOutEdges() { return graph.getOutEdges(this); }
    public List<Edge> getInEdges() { return graph.getInEdges(this); }
    public List<Edge> getIncidentEdges() { return graph.getIncidentEdges(this.getId()); }
    public List<Edge> getEdgesTo(Node u) { return graph.getEdges(this, u); }

    @Override public int compareTo(Node o) { return Integer.compare(this.id, o.id); }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node n = (Node) o;
        return id == n.id && graph == n.graph;
    }
    @Override public int hashCode() { return Objects.hash(id, System.identityHashCode(graph)); }
    @Override public String toString() { return name != null ? name + "(" + id + ")" : Integer.toString(id); }
}
