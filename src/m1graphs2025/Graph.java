package m1graphs2025;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graph {
    protected final Map<Node, List<Edge>> adjEdList = new HashMap<>();
    protected final Map<Node, List<Edge>> inAdjEdList = new HashMap<>();

    public Graph() {}
    public Graph(int... sa) {
        this(fromSA(sa));
    }
//
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

    Node ensureNode(int id) { Node n = getNode(id); if (n != null) return n; addNode(id); return Objects.requireNonNull(getNode(id)); }
    public int largestNodeId() { return adjEdList.keySet().stream().mapToInt(Node::getId).max().orElse(0); }
    public int smallestNodeId() { return adjEdList.keySet().stream().mapToInt(Node::getId).min().orElse(0); }

    // Node-related
    public int nbNodes() { return adjEdList.size(); }
    public boolean usesNode(Node n) { return n != null && getNode(n.getId()) != null; }
    public boolean usesNode(int id) { return getNode(id) != null; }
    public boolean holdsNode(Node n) { return n != null && n.getGraph() == this && usesNode(n.getId()); }
    public Node getNode(int id) { for (Node u : adjEdList.keySet()) if (u.getId() == id) return u; return null; }

    public boolean addNode(Node n) {
        return addNode(n.getId()); }
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

    public List<Node> getAllNodes() {
        List<Node> nodes = new ArrayList<>(adjEdList.keySet()); Collections.sort(nodes); return nodes; }

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

    // Edge-related
    public int nbEdges() {
        return getAllEdges().size();
    }
    public boolean existsEdge(Node u, Node v) {
        return !getEdges(u, v).isEmpty(); }
    public boolean existsEdge(int uid, int vid) {
        return !getEdges(uid, vid).isEmpty(); }
    public boolean existsEdge(Edge e) {
        return getAllEdges().contains(e); }

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
    public boolean removeEdge(int fromId, int toId) {
        return removeEdge(fromId, toId, null); }
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

    public List<Edge> getAllEdges() {
        List<Edge> all = new ArrayList<>();
        for (List<Edge> lst : adjEdList.values()) all.addAll(lst);
        Collections.sort(all);
        return all; }

    // Transforms [46..54]
    public int[] toSuccessorArray() {
        int largestId = largestNodeId();
        List<Integer> sa = new ArrayList<>();
        for (int id = 1; id <= largestId; id++) {
            Node u = getNode(id);
            if (u != null) {
                for (Edge e : getOutEdges(u)) {
                    sa.add(e.to().getId());
                }
            }
            if (id < largestId) sa.add(0); // separator, no trailing zero
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
        Graph closure = new Graph();
        for (Node u : getAllNodes()) closure.addNode(u.getId());
        for (Node u : getAllNodes()) {
            Set<Integer> reachable = new HashSet<>();
            Deque<Node> stack = new ArrayDeque<>();
            stack.push(u);
            while (!stack.isEmpty()) {
                Node x = stack.pop();
                for (Edge e : getOutEdges(x)) {
                    if (!reachable.contains(e.to().getId())) {
                        reachable.add(e.to().getId());
                        stack.push(e.to());
                    }
                }
            }
            for (Integer vId : reachable) closure.addEdge(u.getId(), vId);
        }
        return closure;
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
    public Graph copy() {
        return new Graph(this); }

    //printer
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
/// BFS
    public List<Node> getBFS() { List<Node> nodes = getAllNodes(); return getBFS(nodes.isEmpty() ? null : nodes.get(0)); }
    public List<Node> getBFS(Node u) { return getBFS(u != null ? u.getId() : -1); }
    public List<Node> getBFS(int id) {
        List<Node> result = new ArrayList<>();
        List<Node> nodes = getAllNodes();
        if (nodes.isEmpty()) return result;
        Queue<Node> q = new ArrayDeque<>();
        Set<Integer> seen = new HashSet<>();
        List<Node> startNodes = new ArrayList<>();
        if (id != -1 && getNode(id) != null) startNodes.add(getNode(id));
        for (Node n : nodes) if (startNodes.isEmpty() || !startNodes.contains(n)) startNodes.add(n);
        for (Node s : startNodes) {
            if (seen.contains(s.getId())) continue;
            q.add(s); seen.add(s.getId());
            while (!q.isEmpty()) {
                Node uNode = q.remove();
                result.add(uNode);
                List<Edge> outs = getOutEdges(uNode);
                Collections.sort(outs);
                for (Edge e : outs) {
                    if (!seen.contains(e.to().getId())) { q.add(e.to()); seen.add(e.to().getId()); }
                }
            }
        }
        return result;
    }

    // dfs
    public List<Node> getDFS() { List<Node> nodes = getAllNodes(); return getDFS(nodes.isEmpty() ? null : nodes.get(0)); }
    public List<Node> getDFS(Node u) { return getDFS(u != null ? u.getId() : -1); }
    public List<Node> getDFS(int id) {
        List<Node> order = new ArrayList<>();
        List<Node> nodes = getAllNodes();
        Set<Integer> visited = new HashSet<>();
        if (nodes.isEmpty()) return order;
        // start from lowest id or specified if exists
        List<Node> startNodes = new ArrayList<>();
        if (id != -1 && getNode(id) != null) startNodes.add(getNode(id));
        for (Node n : nodes) if (startNodes.isEmpty() || !startNodes.contains(n)) startNodes.add(n);
        for (Node s : startNodes) {
            if (!visited.contains(s.getId())) dfsCollect(s, visited, order);
        }
        return order;
    }
    public List<Node> getDFSWithVisitInfo(Map<Node, NodeVisitInfo> nodeVisit, Map<Edge, EdgeVisitType> edgeVisit) {
        List<Node> nodes = getAllNodes();
        return getDFSWithVisitInfo(nodes.isEmpty() ? null : nodes.get(0), nodeVisit, edgeVisit);
    }

    public List<Node> getDFSWithVisitInfo(Node start, Map<Node, NodeVisitInfo> nodeVisit, Map<Edge, EdgeVisitType> edgeVisit) {
        if (nodeVisit == null || edgeVisit == null) throw new IllegalArgumentException("Maps cannot be null");
        nodeVisit.clear(); edgeVisit.clear();
        for (Node n : getAllNodes()) nodeVisit.put(n, new NodeVisitInfo());
        List<Node> order = new ArrayList<>();
        int time = 0;
        List<Node> nodes = getAllNodes();
        for (Node s : nodes) {
            if (nodeVisit.get(s).colour == NodeColour.WHITE) {
                time = dfsVisit(s, nodeVisit, edgeVisit, time, order);
            }
        }
        return order;
    }
    private void dfsCollect(Node u, Set<Integer> visited, List<Node> order) {
        visited.add(u.getId());
        order.add(u);
        List<Edge> outs = getOutEdges(u);
        Collections.sort(outs);
        for (Edge e : outs) {
            if (!visited.contains(e.to().getId())) dfsCollect(e.to(), visited, order);
        }
    }
    private int dfsVisit(Node u, Map<Node, NodeVisitInfo> nodeVisit, Map<Edge, EdgeVisitType> edgeVisit, int time, List<Node> order) {
        NodeVisitInfo infoU = nodeVisit.get(u);
        infoU.colour = NodeColour.GRAY;
        time++; infoU.discovery = time;
        order.add(u);
        List<Edge> outs = getOutEdges(u);
        Collections.sort(outs);
        for (Edge e : outs) {
            Node v = e.to();
            NodeVisitInfo infoV = nodeVisit.get(v);
            if (infoV.colour == NodeColour.WHITE) {
                edgeVisit.put(e, EdgeVisitType.TREE);
                infoV.predecessor = u;
                time = dfsVisit(v, nodeVisit, edgeVisit, time, order);
            } else if (infoV.colour == NodeColour.GRAY) {
                edgeVisit.put(e, EdgeVisitType.BACKWARD);
            } else {
                // black
                // determine forward or cross: approximate by discovery times
                if (infoU.discovery != null && infoV.discovery != null && infoU.discovery < infoV.discovery)
                    edgeVisit.put(e, EdgeVisitType.FORWARD);
                else edgeVisit.put(e, EdgeVisitType.CROSS);
            }
        }
        infoU.colour = NodeColour.BLACK;
        time++; infoU.finish = time;
        return time;
    }

    //dot rel
    public static Graph fromDotFile(String filename) throws IOException { return fromDotFile(filename, ".gv"); }
    public static Graph fromDotFile(String filename, String extension) throws IOException {
        Path path = Paths.get(filename + extension);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        Graph g = new Graph();
        Pattern edgePattern = Pattern.compile("\\s*(\\d+)\\s*(->|--)\\s*(\\d+)(\\s*\"?\"?)\\s*(\\[.*\\])?;?\\s*");
        // Better: loosen regex
        Pattern simpleEdge = Pattern.compile("\\s*(\\d+)\\s*(->|--)\\s*(\\d+)(.*)");
        for (String raw : lines) {
            String line = raw.trim();
            if (line.isEmpty()) continue;
            if (line.startsWith("//") || line.startsWith("digraph") || line.startsWith("graph") || line.startsWith("}")) continue;
            Matcher m = simpleEdge.matcher(line);
            if (m.find()) {
                int u = Integer.parseInt(m.group(1));
                int v = Integer.parseInt(m.group(3));
                Integer weight = null;
                String tail = m.group(4);
                if (tail != null && tail.contains("[")) {
                    // findng label= or len=
                    Pattern attr = Pattern.compile("label\\s*=\\s*(\\d+).*len\\s*=\\s*(\\d+)|len\\s*=\\s*(\\d+).*label\\s*=\\s*(\\d+)|label\\s*=\\s*(\\d+)|len\\s*=\\s*(\\d+)");
                    Matcher ma = attr.matcher(tail);
                    if (ma.find()) {
                        for (int i = 1; i <= ma.groupCount(); i++) {
                            String gstr = ma.group(i);
                            if (gstr != null) {
                                try { weight = Integer.parseInt(gstr);
                                    break; }
                                catch (NumberFormatException ex){}
                            }
                        }
                    }
                }
                g.addEdge(u, v, weight);
            }
        }
        return g;
    }
    public void toDotFile(String fileName) throws IOException { toDotFile(fileName, ".gv"); }
    public void toDotFile(String fileName, String extension) throws IOException {
        Path p = Paths.get(fileName + extension);
        Files.writeString(p, toDotString(), StandardCharsets.UTF_8);
    }
    public String toDotString() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n");
        List<Edge> edges = getAllEdges();
        for (Edge e : edges) {
            sb.append(String.format("%d -> %d", e.from().getId(), e.to().getId()));
            if (e.isWeighted()) sb.append(String.format(" [label=%d, len=%d]", e.getWeight(), e.getWeight()));
            sb.append("\n");
        }
        sb.append("}\n");
        return sb.toString();
    }

}
