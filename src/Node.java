import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import static java.lang.Integer.compare;

public class Node implements Comparable<Node>{
    public int id;
    private String name ;
    private final Graph graph ;

    public Node(int id, Graph graph) {
        this(id,null,graph); //trying to put the name
//        this.graph = graph;
//        this.id = id;
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

    public List<Node> getSuccessors() {
        List<Node> succ = new ArrayList<>();
        for (Edge e : getOutEdges()) {
            if (!succ.contains(e.to())) succ.add(e.to());
        }
        Collections.sort(succ);
        return succ;
    }
    public List<Node> getSuccessorsMulti() {
        List<Node> succ = new ArrayList<>();
        for (Edge e : getOutEdges()) {
            succ.add(e.to());
        }
        return succ;
    }
// TODO
public boolean adjacent(int id) {
    for (Edge e : getOutEdges())
        if (e.to().getId() == id)
            return true;
    return false;
}
    public boolean adjacent(Node u) { return adjacent(u != null ? u.getId() : -1); }

    public List<Edge> getOutEdges() { return graph.getOutEdges(this); }
    public List<Edge> getInEdges() { return graph.getInEdges(this); }

    public int inDegree() { return getInEdges().size(); }
    public int outDegree() { return getOutEdges().size(); }
    public int degree() { return inDegree() + outDegree(); }

   public List<Edge> getIncidentEdges(){
       List<Edge> res = new ArrayList<>(getOutEdges());
       for (Edge e : getInEdges()) if (!res.contains(e)) res.add(e);
       return res;
     }
   public List<Edge> getEdgesTo(Node u) {
        return graph.getEdges(this, u); }

}
