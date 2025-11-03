//package m1graphs2025;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Graph {
    protected final Map<Node, List<Edge>> adjEdList = new HashMap<>();
    protected final Map<Node, List<Edge>> inAdjEdList = new HashMap<>();

    // Construction
    public Graph() {}
    public Graph(int... sa) { this(fromSA(sa)); }
//    public Graph(int[] sa) { this(fromSA(sa)); }
    private Graph(Graph g) {
        for (Node u : g.getAllNodes()) this.addNode(u.getId());
        for (Edge e : g.getAllEdges()) this.addEdge(e.from().getId(), e.to().getId(), e.getWeight());
    }
    private static Graph fromSA(int[] sa) {
        Graph g = new Graph();
        int current = 1;
        g.addNode(current);
        for (int i = 0; i < sa.length; i++) {
            int v = sa[i];
            if (v == 0) {
                if (i == sa.length - 1) break; // no trailing empty node
                current++;
                g.addNode(current);
            } else {
                g.addEdge(current, v, null);
            }
        }
        return g;
    }

    // Helpers
    Node ensureNode(int id) { Node n = getNode(id); if (n != null) return n; addNode(id); return Objects.requireNonNull(getNode(id)); }
    public int largestNodeId() { return adjEdList.keySet().stream().mapToInt(Node::getId).max().orElse(0); }
    public int smallestNodeId() { return adjEdList.keySet().stream().mapToInt(Node::getId).min().orElse(0); }

    // Node-related [21..35]
    public int nbNodes() { return adjEdList.size(); }
    public boolean usesNode(Node n) { return n != null && getNode(n.getId()) != null; }
    public boolean usesNode(int id) { return getNode(id) != null; }
    public boolean holdsNode(Node n) { return n != null && n.getGraph() == this && usesNode(n.getId()); }
    public Node getNode(int id) { for (Node u : adjEdList.keySet()) if (u.getId() == id) return u; return null; }

    public boolean addNode(Node n) { return addNode(n.getId()); }
    public boolean addNode(int id) {
        if (usesNode(id)) return false;
        Node n = new Node(id, this);
        adjEdList.put(n, new ArrayList<>());
        inAdjEdList.put(n, new ArrayList<>());
        return true;
    }

    public boolean removeNode(Node n) { return removeNode(n.getId()); }
    public boolean removeNode(int id) {
        Node n = getNode(id); if (n == null) return false;
        for (Edge e : new ArrayList<>(getIncidentEdges(id))) removeEdge(e);
        adjEdList.remove(n); inAdjEdList.remove(n);
        return true;
    }

    public List<Node> getAllNodes() { List<Node> nodes = new ArrayList<>(adjEdList.keySet()); Collections.sort(nodes); return nodes; }

    public List<Node> getSuccessors(Node n) { return getSuccessors(n.getId()); }
    public List<Node> getSuccessors(int id) {
        Node u = Objects.requireNonNull(getNode(id));
        TreeSet<Node> set = new TreeSet<>();
        for (Edge e : adjEdList.get(u)) set.add(e.to());
        return new ArrayList<>(set);
    }

    public List<Node> getSuccessorsMulti(Node n) { return getSuccessorsMulti(n.getId()); }
    public List<Node> getSuccessorsMulti(int id) {
        Node u = Objects.requireNonNull(getNode(id));
        List<Node> list = new ArrayList<>();
        for (Edge e : adjEdList.get(u)) list.add(e.to());
        return list;
    }

    public boolean adjacent(Node u, Node v) { return adjacent(u.getId(), v.getId()); }
    public boolean adjacent(int uid, int vid) { return !getEdges(uid, vid).isEmpty(); }

    public int inDegree(Node n) { return inDegree(n.getId()); }
    public int inDegree(int id) { return getInEdges(id).size(); }
    public int outDegree(Node n) { return outDegree(n.getId()); }
    public int outDegree(int id) { return getOutEdges(id).size(); }
    public int degree(Node n) { return degree(n.getId()); }
    public int degree(int id) { return inDegree(id) + outDegree(id); }

    // Edge-related [36..45]
    public int nbEdges() { int n = 0; for (List<Edge> lst : adjEdList.values()) n += lst.size(); return n; }

    public boolean existsEdge(Node u, Node v) { return !getEdges(u, v).isEmpty(); }
    public boolean existsEdge(int uid, int vid) { return !getEdges(uid, vid).isEmpty(); }
    public boolean existsEdge(Edge e) { return getAllEdges().contains(e); }

    public boolean isMultiEdge(Node u, Node v) { return getEdges(u, v).size() >= 2; }
    public boolean isMultiEdge(int uid, int vid) { return getEdges(uid, vid).size() >= 2; }
    public boolean isMultiEdge(Edge e) { return isMultiEdge(e.from(), e.to()); }

    public void addEdge(Node from, Node to) { addEdge(from.getId(), to.getId(), null); }
    public void addEdge(int fromId, int toId) { addEdge(fromId, toId, null); }
    public void addEdge(Edge e) { addEdge(e.from().getId(), e.to().getId(), e.getWeight()); }
    public void addEdge(Node from, Node to, Integer weight) { addEdge(from.getId(), to.getId(), weight); }

    public void addEdge(int fromId, int toId, Integer weight) {
        Node u = ensureNode(fromId), v = ensureNode(toId);
        Edge e = new Edge(u, v, weight);
        adjEdList.get(u).add(e); inAdjEdList.get(v).add(e);
    }

    public boolean removeEdge(Node from, Node to) { return removeEdge(from.getId(), to.getId(), null); }
    public boolean removeEdge(int fromId, int toId) { return removeEdge(fromId, toId, null); }
    public boolean removeEdge(Edge e) {
        boolean a = adjEdList.get(e.from()).remove(e);
        boolean b = inAdjEdList.get(e.to()).remove(e);
        return a || b;
    }
    public boolean removeEdge(Node from, Node to, Integer weight) { return removeEdge(from.getId(), to.getId(), weight); }
    public boolean removeEdge(int fromId, int toId, Integer weight) {
        Node u = getNode(fromId), v = getNode(toId);
        if (u == null || v == null) return false;
        List<Edge> outs = adjEdList.get(u);
        for (Iterator<Edge> it = outs.iterator(); it.hasNext();) {
            Edge e = it.next();
            if (e.to().equals(v) && (weight == null || Objects.equals(weight, e.getWeight()))) {
                it.remove(); inAdjEdList.get(v).remove(e); return true;
            }
        }
        return false;
    }

    public List<Edge> getOutEdges(Node n) { return getOutEdges(n.getId()); }
    public List<Edge> getOutEdges(int id) { Node u = Objects.requireNonNull(getNode(id)); List<Edge> out = new ArrayList<>(adjEdList.get(u)); Collections.sort(out); return out; }

    public List<Edge> getInEdges(Node n) { return getInEdges(n.getId()); }
    public List<Edge> getInEdges(int id) { Node u = Objects.requireNonNull(getNode(id)); List<Edge> in = new ArrayList<>(inAdjEdList.get(u)); Collections.sort(in); return in; }

    public List<Edge> getIncidentEdges(Node n) { return getIncidentEdges(n.getId()); }
    public List<Edge> getIncidentEdges(int id) {
        List<Edge> res = new ArrayList<>(getOutEdges(id));
        for (Edge e : getInEdges(id)) if (!res.contains(e)) res.add(e);
        Collections.sort(res); return res;
    }

    public List<Edge> getEdges(Node u, Node v) { return getEdges(u.getId(), v.getId()); }
    public List<Edge> getEdges(int uid, int vid) {
        Node u = getNode(uid), v = getNode(vid);
        if (u == null || v == null) return Collections.emptyList();
        List<Edge> res = new ArrayList<>();
        for (Edge e : adjEdList.get(u)) if (e.to().equals(v)) res.add(e);
        Collections.sort(res); return res;
    }

    public List<Edge> getAllEdges() { List<Edge> all = new ArrayList<>(); for (List<Edge> lst : adjEdList.values()) all.addAll(lst); Collections.sort(all); return all; }

    // Transforms [46..54]
    public int[] toSuccessorArray() {
        List<Integer> sa = new ArrayList<>();
        List<Node> nodes = getAllNodes();
        for (int i = 0; i < nodes.size(); i++) {
            Node u = nodes.get(i);
            for (Edge e : getOutEdges(u)) sa.add(e.to().getId());
            if (i < nodes.size() - 1) sa.add(0);
        }
        return sa.stream().mapToInt(Integer::intValue).toArray();
    }

    public int[][] toAdjMatrix() {
        List<Node> nodes = getAllNodes();
        int n = nodes.size();
        Map<Integer,Integer> idx = new HashMap<>();
        for (int i = 0; i < n; i++) idx.put(nodes.get(i).getId(), i);
        int[][] m = new int[n][n];
        for (Edge e : getAllEdges()) {
            int i = idx.get(e.from().getId());
            int j = idx.get(e.to().getId());
            m[i][j] += 1;
        }
        return m;
    }

    public Graph getReverse() {
        Graph r = new Graph();
        for (Node u : getAllNodes()) r.addNode(u.getId());
        for (Edge e : getAllEdges()) r.addEdge(e.to().getId(), e.from().getId(), e.getWeight());
        return r;
    }

    public Graph getTransitiveClosure() {
        Graph tc = new Graph();
        for (Node u : getAllNodes()) tc.addNode(u.getId());
        for (Node u : getAllNodes()) {
            for (Node v : bfsReachableFrom(u))
                if (u.getId() != v.getId()) tc.addEdge(u.getId(), v.getId());
        }
        return tc;
    }
    private List<Node> bfsReachableFrom(Node s) {
        Set<Node> vis = new HashSet<>(); Queue<Node> q = new ArrayDeque<>();
        q.add(s); vis.add(s); List<Node> out = new ArrayList<>();
        while (!q.isEmpty()) {
            Node u = q.poll(); out.add(u);
            for (Node v : getSuccessors(u)) if (!vis.contains(v)) { vis.add(v); q.add(v); }
        }
        return out;
    }

    public boolean isMultiGraph() {
        for (Node u : getAllNodes())
            for (Node v : getSuccessors(u))
                if (getEdges(u, v).size() >= 2) return true;
        return false;
    }
    public boolean hasSelfLoops() { for (Edge e : getAllEdges()) if (e.isSelfLoop()) return true; return false; }
    public boolean isSimpleGraph() { return !hasSelfLoops() && !isMultiGraph(); }
    public Graph toSimpleGraph() {
        Graph s = new Graph();
        for (Node u : getAllNodes()) s.addNode(u.getId());
        Set<String> seen = new HashSet<>();
        for (Edge e : getAllEdges()) {
            if (e.isSelfLoop()) continue;
            String key = e.from().getId() + ":" + e.to().getId();
            if (seen.add(key)) s.addEdge(e.from().getId(), e.to().getId());
        }
        return s;
    }
    public Graph copy() { return new Graph(this); }

    // Pretty printer for your required display
    public String toSuccessorListPretty() {
        StringBuilder sb = new StringBuilder();
        for (Node n : getAllNodes()) {
            sb.append(" ").append(n.getId()).append(" ");
            List<Edge> outs = getOutEdges(n);
            if (!outs.isEmpty()) {
                sb.append(" ");
                for (int i = 0; i < outs.size(); i++) {
                    Edge e = outs.get(i);
                    if (i > 0) sb.append(", ");
                    sb.append("(").append(e.from().getId()).append(", ").append(e.to().getId()).append(")");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
