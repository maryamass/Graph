package pw3;

import m1graphs2025.*;
import java.util.*;

public class FordFulkerson {

    public static int maxFlow(FLowNetwork network, Node s, Node t, String outputPrefix) {
        Flow flow = new Flow();
        int maxFlow = 0;
        int step = 1;

        while (true) {
            ResidualGraph residual = new ResidualGraph(network, flow);
            List<Node> path = bfs(residual, s, t);

            // Export residual graph
            DotExporter.exportResidualGraph(residual, path, step, maxFlow, outputPrefix);

            if (path == null) break;

            // Find residual capacity (bottleneck)
            int bottle = Integer.MAX_VALUE;
            for (int i = 0; i < path.size() - 1; i++) {
                Node u = path.get(i);
                Node v = path.get(i + 1);
                bottle = Math.min(bottle, residual.getResidualCapacity(u, v));
            }

            // Augment flow along the path
            for (int i = 0; i < path.size() - 1; i++) {
                Node u = path.get(i);
                Node v = path.get(i + 1);
                int currentFlow = flow.getFlow(u, v);
                int reverseFlow = flow.getFlow(v, u);

                if (network.getCapacity(u, v) > 0)
                    flow.setFlow(u, v, currentFlow + bottle);
                else
                    flow.setFlow(v, u, reverseFlow - bottle);
            }

            maxFlow += bottle;

            // Export flow after this step
            DotExporter.exportFlow(network, flow, step, maxFlow, outputPrefix);

            step++;
        }

        return maxFlow;
    }

    private static List<Node> bfs(ResidualGraph residual, Node s, Node t) {
        Map<Node, Node> parent = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(s);

        while (!queue.isEmpty()) {
            Node u = queue.poll();
            for (Node v : residual.getSuccessors(u)) {
                if (!parent.containsKey(v) && residual.getResidualCapacity(u, v) > 0) {
                    parent.put(v, u);
                    if (v.equals(t)) return buildPath(parent, s, t);
                    queue.add(v);
                }
            }
        }
        return null;
    }

    private static List<Node> buildPath(Map<Node, Node> parent, Node s, Node t) {
        List<Node> path = new ArrayList<>();
        for (Node v = t; v != null; v = parent.get(v)) path.add(0, v);
        return path;
    }
}
