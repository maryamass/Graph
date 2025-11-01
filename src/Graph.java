import java.lang.reflect.Array;
import java.util.*;

public class Graph {
    Map<Node, List<Edge>> adjEdList;
    protected List<Edge> E_set;
    protected List<Node> N_set;


    public Graph(int... integers) {   //
        int count = 0;
        for (int integer : integers) {
            if (integer == 0)
                count++;
        }
        for (int i = 0; i < count; i++) {
            Node n = new Node(i + 1, this);
            N_set.set(i, n);
        }
        for (int j=0;j< count;j++){
            for (int i=0; i<integers.length; i++) {
                if (integers[i]==0){
                    break;
                }
                Edge e = new Edge(N_set.get(j).getId(),integers[i], this );
                E_set.add(e);
            }
            adjEdList.put(N_set.get(j) ,E_set);
        }
         }

    public void addEdge(Edge e) {
        System.out.print("edge added");
    }

    public Node getNode(int id) {
        for (Node u : adjEdList.keySet()) if (u.getId() == id) return u;
        return null;
    }

    public List<Node> getSuccessors(Node n) {
        return getSuccessors(n.getId()); }
    public List<Node> getSuccessors(int id) {
        Node u = getNode(id);
        if (u == null) return new ArrayList<>();
        return u.getSuccessors();
    }

    public boolean adjacent(Node u, Node v){
        return true;
    }

    public List<Edge> getOutEdges(Node n) { return getOutEdges(n.getId()); }
    public List<Edge> getOutEdges(int id) {
        Node u = getNode(id);
        if (u == null) return new ArrayList<>();
        List<Edge> out = adjEdList.getOrDefault(u, new ArrayList<>());
        return new ArrayList<>(out);
    }
}