package m1graphs2025;

import java.util.*;

/**
 * @author Maryam Assmar
 * @author Issa Hassane Abdramane
 */
public class UndirectedGraph extends Graph {

    /**
     * Add an undirected edge. Stores two symmetric arcs (u->v and v->u) with identical weight.
     * @param e edge whose endpoints and weight are used
     */
    @Override
    public void addEdge(Edge e) {
        if (e == null) return;
        Node a = e.from(); Node b = e.to();
        if (!usesNode(a.getId())) addNode(a);
        if (!usesNode(b.getId())) addNode(b);
        // add both with same weight
        Node an = getNode(a.getId()); Node bn = getNode(b.getId());
        List<Edge> la = adjEdList.get(an);
        if (la == null) {
            la = new ArrayList<>(); adjEdList.put(an, la); }
        la.add(new Edge(an, bn, e.getWeight()));
        List<Edge> lb = adjEdList.get(bn);
        if (lb == null) {
            lb = new ArrayList<>(); adjEdList.put(bn, lb); }
        lb.add(new Edge(bn, an, e.getWeight()));
    }

    @Override
    public void addEdge(Node from, Node to, Integer weight) {
        addEdge(new Edge(from, to, weight));
    }

    @Override
    public void addEdge(int fromId, int toId, Integer weight) {
        Node a = getNode(fromId); if (a == null) addNode(fromId); a = getNode(fromId);
        Node b = getNode(toId); if (b == null) addNode(toId); b = getNode(toId);
        addEdge(new Edge(a, b, weight));
    }

    @Override
    public boolean removeEdge(int fromId, int toId) {
        // remove both directions
        boolean r1 = super.removeEdge(fromId, toId);
        boolean r2 = super.removeEdge(toId, fromId);
        return r1 || r2;
    }

    @Override
    public List<Edge> getAllEdges() {
        // for undirected, edges are stored twice; we return unique set by canonical ordering
        List<Edge> all = super.getAllEdges();
        Set<String> seen = new HashSet<>();
        List<Edge> res = new ArrayList<>();
        for (Edge e : all) {
            int a = Math.min(e.from().getId(), e.to().getId());
            int b = Math.max(e.from().getId(), e.to().getId());
            String k = a+"-"+b + (e.getWeight()==null?"":("#"+e.getWeight()));
            if (!seen.contains(k)) { seen.add(k); res.add(e); }
        }
        Collections.sort(res);
        return res;
    }
}
