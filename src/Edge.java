import java.util.Objects;

public class Edge {
    public Node from ;
    public Node to ;
    private int weight = 0;
    Graph g= new Graph();

    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
    }
    public Edge(int id1, int id2, Graph g) {

            if (this.from.getGraph() == this.to.getGraph()) {
                this.from.id = id1;
                this.to.id = id2;
                this.g = g;
            }
            else
                throw new IllegalArgumentException("graph not matched") ;
    }
    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return weight == edge.weight && Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, weight);
    }
}
