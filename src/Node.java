import java.util.List;
import java.util.Objects;

import static java.lang.Integer.compare;

public class Node implements Comparable<Node>{
    private int id;
    private String name ;
    private Graph graph ;

    public Node(int id, Graph graph) {
        this.graph = graph;
        this.id = id;
    }

    public Node(int id, String name, Graph graph) {
        this.id = id;
        this.name = name;
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
//    public List<Node> getSuccessor(){
//
//        return List.of();
//    }
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

    public Node(Graph graph) {
        this.graph = graph;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, graph);
    }
}
