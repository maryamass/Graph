package pw3;

import m1graphs2025.*;
import java.util.*;

public class FordFulkerson {

    public static int maxFlow(FLowNetwork network, Node s, Node t, String outputPrefix) {
        Flow flow = new Flow();
        int maxFlow = 0;
        int step = 1;
        int maxIterations = 10;
        int iter = 0;

        if (s == null || t == null) {
            throw new IllegalArgumentException("Source or sink is null!");
        }
        if (!network.getAllNodes().contains(s) || !network.getAllNodes().contains(t)) {
            throw new IllegalArgumentException("Source or sink not in graph!");
        }

        while (true) {
            iter++;
            if (iter > maxIterations) {
                System.err.println("Max iterations reached. Stopping.");
                break;
            }

            // Build res gra
            ResidualGraph residual = new ResidualGraph();
            Map<Integer, Node> residMap = new HashMap<>();
            for (Node orig : network.getAllNodes()) {
                residual.addNode(orig.getId());
                Node r = residual.getNode(orig.getId());
                residMap.put(orig.getId(), r);
            }
            for (Edge e : network.getAllEdges()) {
                Node uOrig = e.from();
                Node vOrig = e.to();
                int cap = network.getCapacity(uOrig, vOrig);
                int curFlow = flow.getFlow(uOrig, vOrig);
                int forward = cap - curFlow;
                int backward = curFlow;
                Node ru = residMap.get(uOrig.getId());
                Node rv = residMap.get(vOrig.getId());
                if (forward > 0) {
                    residual.addResidualEdge(ru, rv, forward);
                }
                if (backward > 0) {
                    residual.addResidualEdge(rv, ru, backward);
                }
            }
            System.out.println("=== Residual Graph ===");
            for (Edge e : residual.getAllEdges()) {
                System.out.printf("%d->%d: %d%n",
                        e.from().getId(), e.to().getId(),
                        residual.getResidualCapacity(e.from(), e.to()));
            }

            System.out.println("=== Residual Graph (Iteration " + iter + ") ===");
            System.out.println("Nodes: " + residual.getAllNodes().size());
            System.out.println("Edges: " + residual.getAllEdges().size());

            Node rs = residMap.get(s.getId());
            Node rt = residMap.get(t.getId());
            List<Node> path = bfs(residual, rs, rt);
            DotExporter.exportResidualGraph(residual, path, step, maxFlow, outputPrefix);
            if (path == null) {
                break; // No more augmenting paths
            }

            int cmin = Integer.MAX_VALUE;
            for (int i = 0; i < path.size() - 1; i++) {
                Node ru = path.get(i);
                Node rv = path.get(i + 1);
                int rc = residual.getResidualCapacity(ru, rv);
                cmin = Math.min(cmin, rc);
            }
            System.out.println("Iteration " + iter + ": path=" + path + " =" + cmin);
            System.out.println("BFS path: " + path);
            for (int i = 0; i < path.size() - 1; i++) {
                Node ru = path.get(i);
                Node rv = path.get(i + 1);
                Node uOrig = network.getNode(ru.getId());
                Node vOrig = network.getNode(rv.getId());
                if (network.getCapacity(uOrig, vOrig) > 0) {
                    int prev = flow.getFlow(uOrig, vOrig);
                    flow.setFlow(uOrig, vOrig, prev + cmin);
                } else {
                    int prev = flow.getFlow(vOrig, uOrig);
                    flow.setFlow(vOrig, uOrig, prev - cmin);
                }
            }
            maxFlow += cmin;
            DotExporter.exportFlow(network, flow, step, maxFlow, outputPrefix);
            step++;
        }

//        System.out.println("Maximum flow value: " + maxFlow);
        return maxFlow;
    }
    private static List<Node> bfs(ResidualGraph residual, Node s, Node t) {
        Map<Node, Node> parent = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        queue.add(s);
        visited.add(s);
        parent.put(s, null);

        while (!queue.isEmpty()) {
            Node u = queue.poll();
            for (Node v : residual.getSuccessors(u)) {
                if (!visited.contains(v) && residual.getResidualCapacity(u, v) > 0) {
                    visited.add(v);
                    parent.put(v, u);
                    if (v.equals(t)) {
                        return buildPath(parent, s, t);
                    }
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
