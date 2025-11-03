import java.util.Collections;
import java.util.List;

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
        System.out.println(""+g.nbNodes()+" nodes, "+g.nbEdges()+" edges");
        for (Node n : g.getAllNodes()) {
            System.out.println(n.getId() + " -> " + g.getSuccessorsMulti(n));
        }
        System.out.println("the needed implementation");

        System.out.print(g.toSuccessorListPretty());
//        System.out.println(">> Nodes: ");
//        List<Node> nodes = g.getAllNodes();
//        Collections.sort(nodes);
//        for (Node n : nodes)
//            System.out.println("Node " + n );



    }
}