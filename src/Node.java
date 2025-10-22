import java.util.ArrayList;
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
// #############TODO ########################

    public List<Node> getSuccessors(){
        return graph.getSuccessors(this);
    }

    public boolean adjacent(Node u) {
        return true;
        //return graph.adjacent(this,Node e);
    }
   public List<Edge> getOutEdges(){
      // for (int i = 0; i < this.getOutEdges().get(); i++) {
      // }

        return new ArrayList<>();
   }

   public List<Node> getSuccessor(){

        List<Node> l= new ArrayList<>();
    return l;
    }

    public List<Node> getSuccessorsMulti(){
        return new ArrayList<>();
    }

    public  List<Edge> getInEdges(){
         return new ArrayList<>();
     }

     public List<Edge> getIncidentEdges(){
         return new ArrayList<>();
     }

     public List<Edge> getEdgesTo(Node u){
        return new ArrayList<>();
    }

}
