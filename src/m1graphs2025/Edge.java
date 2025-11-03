package m1graphs2025;

import java.util.Objects;

public final class Edge implements Comparable<Edge> {
    private final Node from;
    private final Node to;
    private final Integer weight; // null = unweighted

    public Edge(Node from, Node to) { this(from, to, null); }
    public Edge(Node from, Node to, Integer weight) {
        if (from == null || to == null) throw new IllegalArgumentException("nodes cannot be null");
        if (from.getGraph() != to.getGraph()) throw new IllegalArgumentException("nodes must share graph");
        this.from = from; this.to = to; this.weight = weight;
    }

    public Edge(int fromId, int toId, Graph g) { this(g.ensureNode(fromId), g.ensureNode(toId), null); }
    public Edge(int fromId, int toId, Integer w, Graph g) { this(g.ensureNode(fromId), g.ensureNode(toId), w); }

    public Node from() { return from; }
    public Node to() { return to; }
    public Edge getSymmetric() { return new Edge(to, from, weight); }
    public boolean isSelfLoop() { return from.getId() == to.getId(); }
    public boolean isMultiEdge() { return from.getGraph().isMultiEdge(from, to); }
    public boolean isWeighted() { return weight != null; }
    public Integer getWeight() { return weight; }

    @Override public int compareTo(Edge o) {
        int c1 = Integer.compare(from.getId(), o.from.getId()); if (c1 != 0) return c1;
        int c2 = Integer.compare(to.getId(), o.to.getId()); if (c2 != 0) return c2;
        int w1 = weight == null ? 0 : weight;
        int w2 = o.weight == null ? 0 : o.weight;
        return Integer.compare(w1, w2);
    }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge e = (Edge) o;
        return Objects.equals(from, e.from) && Objects.equals(to, e.to) && Objects.equals(weight, e.weight);
    }
    @Override public int hashCode() { return Objects.hash(from, to, weight); }
    @Override public String toString() {
        return "(" + from.getId() + " -> " + to.getId() + (weight != null ? ", w=" + weight : "") + ")";
    }
}
