import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    Map<Node, List<Edge>> adjEdList;
    protected Set<Node> N_set;
    protected List<Edge> E_set;

    public Graph(int... integers) {   //

        for (int i=0; i < integers.length; i++){
            analyse();
        }
    }

    private void analyse() {

    }
}