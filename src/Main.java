import m1graphs2025.*;
import pw3.FLowNetwork;
import pw3.Flow;
import pw3.FordFulkerson;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("*--------------------------------------------------------------------*");
        System.out.println("************ PART 1. UNWEIGTED DIRECTED GRAPHS ***********************");
        System.out.println("*--------------------------------------------------------------------*");
        System.out.println("\n>>>>>>>> SIMPLE GRAPH >>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(">>>>>>>> Creating the subject example graph in G");

        Graph g = new Graph(2, 4, 0, 0, 6, 0, 2, 3, 5, 8, 0, 0, 4, 7, 0, 3, 0, 7, 0);
        System.out.println(">>>> Graph information");
//        System.out.println(">> DOT representation\n"+g.toDotString());
        System.out.println("" + g.nbNodes() + " nodes, " + g.nbEdges() + " edges");
        for (Node n : g.getAllNodes()) {
            System.out.println(n.getId() + " -> " + g.getSuccessorsMulti(n));
        }
        System.out.println("the needed implementation");

        System.out.print(g.toSuccessorListPretty());
        int[] sa = g.toSuccessorArray();
        System.out.println(Arrays.toString(sa));
        System.out.println(">> Nodes: ");
        List<Node> nodes = g.getAllNodes();
        Collections.sort(nodes);
        for (Node n : nodes)
            System.out.println("Node " + n);
        /// exp2
        Graph g2 = new Graph();
        g2.addEdge(1, 2);
        g2.addEdge(1, 4);
        g2.addEdge(3, 6);
        g2.addEdge(4, 2);
        g2.addEdge(4, 3);
        g2.addEdge(4, 5);
        g2.addEdge(4, 8);
        g2.addEdge(6, 4);
        g2.addEdge(6, 7);
        g2.addEdge(7, 3);
        g2.addEdge(8, 7);

        System.out.println("Nodes: " + g2.getAllNodes());
        System.out.println("Edges: " + g2.getAllEdges());
        System.out.println("DFS: " + g2.getDFS());
        System.out.println("BFS: " + g2.getBFS());
        System.out.println("Dot:\n" + g2.toDotString());

        int[] s2 = g2.toSuccessorArray();
        System.out.println("SA: " + Arrays.toString(s2));
        boolean b = g2.isMultiGraph();
        if (!b)
            System.out.println("g2 is a multigraph");
        int[][] mat = g2.toAdjMatrix();
        System.out.println("Adj matrix:");
        for (int i = 0; i < mat.length; i++)
            System.out.println(Arrays.toString(mat[i]));

        Graph tr = g2.getTransitiveClosure();

        System.out.println("Transitive edges: " + tr.getAllEdges());

        Map<Node, NodeVisitInfo> nvi = new HashMap<>();
        Map<Edge, EdgeVisitType> evi = new HashMap<>();
        g.getDFSWithVisitInfo(nvi, evi);
        System.out.println("Visit info:");
        for (Map.Entry<Node, NodeVisitInfo> en : nvi.entrySet()) {
            System.out.println(en.getKey().getId() + ": d=" + en.getValue().discovery + ", f=" +
                    en.getValue().finish + ", pred=" +
                    (en.getValue().predecessor == null ? "null" : en.getValue().predecessor.getName()));
        }


        //______________________________________________
        try {
            g.toDotFile("testGraph", ".dot");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Graph f;
//        System.out.println(System.getProperty("user.dir"));
        try {
            f = Graph.fromDotFile("C:\\Users\\marya\\IdeaProjects\\m1graphs2025\\testGraph");
            System.out.println("Nodes: " + f.getAllNodes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("*-----------------------------------------------------------------------*");
        System.out.println("************   PART 6. DFS and Node Visit Info   ***********************");
        System.out.println("*-----------------------------------------------------------------------*");

        // Lecture VS Example
        Graph gLecture = Graph.fromDotFile("C:\\Users\\marya\\IdeaProjects\\m1graphs2025\\testGraph");
        System.out.println("Graph read as:");
        System.out.println(gLecture.toDotString());

        Map<Node, NodeVisitInfo> nodeVisit = new HashMap<Node, NodeVisitInfo>();
        Map<Edge, EdgeVisitType> edgeVisit = new HashMap<Edge, EdgeVisitType>();

        gLecture.getDFSWithVisitInfo(nodeVisit, edgeVisit);

        System.out.println("Nodes visit info\n-----------------\n");
        for (Node u : gLecture.getAllNodes()) {
            System.out.println(u + ": " + nodeVisit.get(u));
        }
//
        System.out.println("Edges visit info\n-----------------\n");
        for (Edge e : gLecture.getAllEdges()) {
            System.out.println(e + ": " + edgeVisit.get(e));
        }
////
//        FLowNetwork flow = new FLowNetwork();
//        flow.addEdge(1, 2, 3);
//        flow.addEdge(1, 4, 7);
//        flow.addEdge(3, 6, 8);
//        flow.addEdge(4, 2, 4);
//        flow.addEdge(4, 3, 2);
//        flow.addEdge(4, 5, 0);
//        System.out.println("Nodes: " + flow.getAllNodes());
//        System.out.println("Edges: " + flow.getAllEdges());
//        System.out.println("Capacities: " + flow.getCapacities());
//
//        Node s = flow.getNode(1);
//        Node t = flow.getNode(5);
//        if (s == null || t == null) {
//            throw new RuntimeException("Source or Sink node is NULL!");
//        }
//        int maxFlow = FordFulkerson.maxFlow(flow, s, t, "output");
//        System.out.println(" Maximum flow value: " + maxFlow);
//
////        //int maxFlow = FordFulkerson.maxFlow(network, s, t, "output");
//        System.out.println(" Maximum flow value: " + maxFlow);

        FLowNetwork flow = new FLowNetwork();
        flow.addEdge(1, 2, 16);
        flow.addEdge(1, 3, 13);
        flow.addEdge(2, 3, 10);
        flow.addEdge(2, 4, 12);
        flow.addEdge(3, 2, 4);
        flow.addEdge(3, 5, 14);
        flow.addEdge(4, 3, 9);
        flow.addEdge(4, 6, 20);
        flow.addEdge(5, 4, 7);
        flow.addEdge(5, 6, 4);
        Node s = flow.getNode(1);
        Node t = flow.getNode(6);
        if (s == null || t == null) {
            throw new RuntimeException("Source or Sink node is NULL!");
        }
        System.out.println("Nodes: " + flow.getAllNodes());
        System.out.println("Edges: " + flow.getAllEdges());
        System.out.println("Capacities: " + flow.getCapacities());
//
        int maxFlow = FordFulkerson.maxFlow(flow, s, t, "output");
        System.out.println("Maximum flow value: " + maxFlow);

    }
}