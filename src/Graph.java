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
    public int nbNodes() {
        return adjEdList.size(); }

    public boolean usesNode(int id) {
        for (Node u : adjEdList.keySet()) if (u.getId() == id) return true;
        return false;
    }
    public boolean usesNode(Node n) {
        if (n == null) return false;
        return usesNode(n.getId());
    }
    public boolean holdsNode(Node n) {
        if (n == null) return false;
        for (Node u : adjEdList.keySet()) if (u.getId() == n.getId() && u.getGraph() == n.getGraph()) return true;
        return false;
    }
    public boolean addNode(int id) {
        if (usesNode(id)) return false;
        Node n = new Node(id, this);
        adjEdList.put(n, new ArrayList<>());
        return true;
    }
    public Node getNode(int id) {
        for (Node u : adjEdList.keySet())
            if (u.getId() == id)
                return u;
        return null;
    }
//    public void addEdge(Node from, Node to, Integer weight) {
//        if (!usesNode(from.getId()))
//            addNode(from);
//        if (!usesNode(to.getId()))
//            addNode(to);
//        addEdge(new Edge(from, to, weight));
//    }
//    public void addEdge(Node from, Node to) {
//        addEdge(from, to, null);
//    }
    public void addEdge(int fromId, int toId) {
        addEdge(fromId, toId, null);
    }
//    public void addEdge(int fromId, int toId, Integer weight) {
//        Node from = getNode(fromId); if (from == null) addNode(fromId); from = getNode(fromId);
//        Node to = getNode(toId); if (to == null) addNode(toId); to = getNode(toId);
//        addEdge(new Edge(from, to, weight));
//    }

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

    public List<Edge> getOutEdges(Node n) {
        return getOutEdges(n.getId());
    }
    public List<Edge> getOutEdges(int id) {
        Node u = getNode(id);
        if (u == null) return new ArrayList<>();
        List<Edge> out = adjEdList.getOrDefault(u, new ArrayList<>());
        return new ArrayList<>(out);
    }

    public List<Edge> getInEdges(Node n) {
        return getInEdges(n.getId()); }
    public List<Edge> getInEdges(int id) {
        List<Edge> res = new ArrayList<>();
        for (Map.Entry<Node, List<Edge>> entry : adjEdList.entrySet()) {
            for (Edge e : entry.getValue())
                if (e.to().getId() == id) res.add(e);
        }
        return res;
    }
    public List<Node> getAllNodes() {
        List<Node> nodes = new ArrayList<>(adjEdList.keySet());
        Collections.sort(nodes);
        return nodes;
    }
}