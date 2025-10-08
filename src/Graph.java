import java.lang.reflect.Array;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    Map<Node, List<Edge>> adjEdList;
    protected List<Edge> E_set;
    protected List<Node> N_set;

    public Graph(int... integers) {   //
        int count = 0;
        for (int i = 0; i < integers.length; i++) {
            if (integers[i] == 0)
                count++;
        }
        for (int i = 0; i < count; i++) {
            Node n = new Node(i + 1, String.valueOf(i + 1), this);
            N_set[i] = n;
        }
        for (int j=0;j< count;j++){
            for (int i=0; i<integers.length; i++) {
                if (integers[i]==0){
                    break;
                }
                Edge e = new Edge(N_set[j].getId(),integers[i], this );

        }
        }
    }

    private void analyse() {

    }
}