public class Main {

    public static void main(String[] args) {
        Graph g = new Graph(2, 4, 0, 0, 6, 0, 2, 3, 5, 8, 0, 0, 4, 7, 0, 3, 0, 7, 0);
        g.addEdge(new Edge(4, 3, g));
        g.addEdge(new Edge(3, 6, g));
        g.addEdge(new Edge(7, 3, g));
        g.addEdge(new Edge(12, 3, g));
        //g.addEdge(3, 25);
        System.out.println("THIS IS A GRAPH"+ g);
    }
// Node f = new Node(1,"A");

}