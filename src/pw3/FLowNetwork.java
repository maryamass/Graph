package pw3;
import m1graphs2025.*;
import m1graphs2025.Graph;
import m1graphs2025.Node;

import java.util.HashMap;
import java.util.Map;

public class FLowNetwork extends Graph {
    private Map<Edge, Integer> capacity;

//    Graph g=new Graph();
//    Node source = new Node( 1, "s", g);
//    Node sink = new Node( 6, "t", g);
//    Edge edge;

    public void FlowNetwork() {
        capacity = new HashMap<>();
    }

    public void addEdge(Node from, Node to, int cap) {
        super.addEdge(from, to);
        capacity.put(new Edge(from, to), cap);
    }


}
