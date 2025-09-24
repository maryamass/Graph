import java.util.Objects;

import static java.lang.Integer.compare;

public class Node implements Comparable<Node>{
    private final int id;
    private String name ;
    private Graph graph ;

    public Node(Graph graph, int id) {
        this.graph = graph;
        this.id = id;
    }

    public Graph getGraph() {
        return graph;
    }
    @Override
    public int compareTo(Node o) {
        return compare(this.id, o.id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id && Objects.equals(name, node.name) && Objects.equals(graph, node.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, graph);
    }
}
