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

    public FLowNetwork() {
        super();
        this.capacity = new HashMap<>();
    }
    //to use dot file
    public FLowNetwork(Graph g) {
        super();
        this.capacity = new HashMap<>(); // Initialize capacity map
        // Copy nodes
        for (Node n : g.getAllNodes()) {
            this.addNode(n.getId());
        }
        for (Edge e : g.getAllEdges()) {
            int from = e.from().getId();
            int to = e.to().getId();
            int cap = e.getWeight(); // Assuming weight is capacity
            this.addEdge(from, to, cap);
        }
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
            throw new RuntimeException("Edge was not created correctly in Graph");
        }

        capacity.put(realEdge, cap);
    }

    public int getCapacity(Node u, Node v) {
        for (Edge e : capacity.keySet()) {
            if (e.from().getId() == u.getId() && e.to().getId() == v.getId())
                return capacity.get(e);
        }
        return 0;
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
}
