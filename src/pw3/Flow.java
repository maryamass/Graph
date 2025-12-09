package pw3;
import m1graphs2025.*;

import java.util.*;

public class Flow {
    private Map<Edge, Integer> flow = new HashMap<>();

    public void setFlow(Node from, Node to, int value) {
        flow.put(new Edge(from, to), value);
    }

    public int getFlow(Node from, Node to) {
        for (Edge e : flow.keySet()) {
            if (e.from().getId() == from.getId() &&
                    e.to().getId() == to.getId()) {
                return flow.get(e);
            }
        }
        return 0;
    }
}