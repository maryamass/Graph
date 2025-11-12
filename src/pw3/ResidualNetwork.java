package pw3;

import m1graphs2025.*;

import java.util.*;

public class ResidualNetwork {
    private Map<Edge, Integer> residualCapacity = new HashMap<>();

    public int getResidualCapacity(Node from, Node to) {
        return residualCapacity.getOrDefault(new Edge(from, to), 0);
    }

    public Map<Edge, Integer> getResidualCapacities() {
        return residualCapacity;
    }
}
