package pw3;

import m1graphs2025.*;
import java.util.*;

public class ResidualGraph extends Graph {
    private Map<Edge, Integer> residualCapacity = new HashMap<>();

    public ResidualGraph(FLowNetwork network, Flow flow) {
        for (Node u : network.getNodes()) {
            addNode(u);
        }

        for (Edge e : network.getEdges()) {
            Node u = e.from();
            Node v = e.to();
            int capacity = network.getCapacity(u, v);
            int f = flow.getFlow(u, v);

            if (capacity - f > 0) {
                addEdge(u, v);
                residualCapacity.put(new Edge(u, v), capacity - f);
            }

            if (f > 0) {
                addEdge(v, u);
                residualCapacity.put(new Edge(v, u), f);
            }
        }
    }
    public int getResidualCapacity(Node from, Node to) {
        return residualCapacity.getOrDefault(new Edge(from, to), 0);
    }

    public Map<Edge, Integer> getResidualCapacities() {
        return residualCapacity;
    }
}
