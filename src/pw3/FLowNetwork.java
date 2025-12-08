package pw3;
import m1graphs2025.*;
import m1graphs2025.Graph;
import m1graphs2025.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FLowNetwork extends Graph {
    private Map<Edge, Integer> capacity;

    static Graph g= new Graph();
    static Node source = g.getNode(1);
    static Node sink = g.getNode(6);
//    Edge edge;

    public FLowNetwork() {
        super();
        this.capacity = new HashMap<>();
    }

    public void addEdge(int from, int to, int cap) {
        super.addEdge(from, to);

        Edge realEdge = null;
        for (Edge e : super.getAllEdges()) {
            if (e.from().getId() == from && e.to().getId() == to) {
                realEdge = e;
                break;
            }
        }

        if (realEdge == null) {
            throw new RuntimeException("Edge was not created correctly in Graph!");
        }

        capacity.put(realEdge, cap);
    }


    public int getCapacity(Node u, Node v) {
        return capacity.getOrDefault(new Edge(u, v), 0);
    }

    public Map<Edge, Integer> getCapacities() {
        return capacity;
    }
    public Set<Edge> getEdges() {
        return capacity.keySet();
    }

    public List<Node> getNodes() {
        return super.getAllNodes();
    }

    public Node getNodeByLabel(String s) {
        return source;
    }
}
