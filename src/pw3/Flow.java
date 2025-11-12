package pw3;
import m1graphs2025.*;

import java.util.*;

public class Flow {
    private Map<Edge, Integer> flow = new HashMap<>();

    public void setFlow(Node from, Node to, int value) {
        flow.put(new Edge(from, to), value);
    }
}
