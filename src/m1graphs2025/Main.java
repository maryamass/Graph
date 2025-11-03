package m1graphs2025;

import java.util.*;

public class Main {

    public static void main(String[] args) {
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
         System.out.println("Node " + n );
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

        int[] s2 = g.toSuccessorArray();
        System.out.println("SA: " + Arrays.toString(s2));

        int[][] mat = g.toAdjMatrix();
        System.out.println("Adj matrix:");
        for (int i = 0; i < mat.length; i++)
            System.out.println(Arrays.toString(mat[i]));

        Graph tr = g.getTransitiveClosure();
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
    }
}