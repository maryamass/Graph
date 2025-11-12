package pw3;
import m1graphs2025.*;
import m1graphs2025.Graph;
import m1graphs2025.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FLowNetwork extends Graph {
    private Map<Edge, Integer> capacity;

    Node source = new Node( 1, "s", this);
    Node sink = new Node( 6, "t", this);

    public void FlowNetwork() {
        capacity = new HashMap<>();
    }

    public void addEdge(Node from, Node to, int cap) {
        super.addEdge(from, to);
        capacity.put(new Edge(from, to), cap);
    }
    public int getCapacity(Node from, Node to) {
        return capacity.getOrDefault(new Edge(from, to), 0);
    }
    public Set<Edge> getEdges() {
        return capacity.keySet();
    }

    public Map<Edge, Integer> getCapacities() {
        return capacity;
    }
}
