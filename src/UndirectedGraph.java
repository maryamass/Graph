//package m1graphs2025;

public class UndirectedGraph extends Graph {
    public void addEdge(int fromId, int toId) { addEdge(fromId, toId, null); }
    public void addEdge(int fromId, int toId, Integer weight) {
        super.addEdge(fromId, toId, weight);
        super.addEdge(toId, fromId, weight);
    }
}
