package pw3;

import m1graphs2025.*;
import java.util.*;

public class ResidualGraph extends Graph {

    private Map<Edge, Integer> residualCapacity = new HashMap<>();

    public ResidualGraph() {
        super();
    }

    public void addResidualEdge(Node u, Node v, int cap) {
        super.addEdge(u.getId(), v.getId(), cap);
        Edge realEdge = null;
        for (Edge e : super.getAllEdges()) {
            if (e.from().getId() == u.getId() && e.to().getId() == v.getId()) {
                realEdge = e;
                break;
            }
        }
        if (realEdge == null) {
            throw new RuntimeException("Edge not found for " + u.getId() + "->" + v.getId());
        }
        residualCapacity.put(realEdge, cap);
    }

    public int getResidualCapacity(Node u, Node v) {
        for (Edge e : residualCapacity.keySet()) {
            if (e.from().getId() == u.getId() &&
                    e.to().getId() == v.getId()) {
                return residualCapacity.get(e);
            }
        }
        return 0;
    }

    public void setResidualCapacity(Node u, Node v, int value) {
        for (Edge e : residualCapacity.keySet()) {
            if (e.from().getId() == u.getId() &&
                    e.to().getId() == v.getId()) {
                residualCapacity.put(e, value);
                return;
            }
        }
    }

    private Edge getEdgeFromTo(int from, int to) {
        for (Edge e : super.getAllEdges()) {
            if (e.from().getId() == from &&
                    e.to().getId() == to) {
                return e;
            }
        }
        throw new RuntimeException("Residual edge not found!");
    }

    public Map<Edge, Integer> getResidualCapacities() {
        return residualCapacity;
    }
}

