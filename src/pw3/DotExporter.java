package pw3;
import m1graphs2025.*;
import java.io.*;
import java.util.*;

public class DotExporter {
    public static void exportFlow(FLowNetwork network, Flow flow, int step, int value, String prefix) {
        try (PrintWriter pw = new PrintWriter(new File(prefix + "_flow" + step + ".gv"))) {
            pw.println("digraph flow" + step + " {");
            pw.println("  rankdir=\"LR\"");
            pw.println("  label=\"(" + step + ") Flow induced from residual graph. Value: " + value + "\"");
            for (Edge e : network.getEdges()) {
                Node u = e.from();
                Node v = e.to();
                int f = flow.getFlow(u, v);
                int c = network.getCapacity(u, v);
                pw.println("  " + u.getId() + " -> " + v.getId() +
                        " [label=\"" + f + "/" + c + "\", len=" + c + "];");
            }
            pw.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportResidualGraph(ResidualGraph residual, List<Node> path, int step, int currentFlow, String prefix) {
        try (PrintWriter pw = new PrintWriter(new File(prefix + "_residual" + step + ".gv"))) {
                pw.println("digraph residualGraph" + step + " {");
                pw.println("  rankdir=\"LR\"");
                String pathLabel = (path != null) ?
                        String.format("(%d) residual graph. Augmenting path: %s. Residual capacity: %d", step, path, currentFlow) :
                        String.format("(%d) residual graph. Augmenting path: none. Previous flow was maximum.", step);
                pw.println("  label=\"" + pathLabel + "\"");

                Set<String> pathEdges = new HashSet<>();
                if (path != null) {
                    for (int i = 0; i < path.size() - 1; i++) {
                        String key = path.get(i).getId() + "->" + path.get(i + 1).getId();
                        pathEdges.add(key);
                    }
                }

                for (Edge e : residual.getAllEdges()) {
                    String key = e.from().getId() + "->" + e.to().getId();
                    String style = pathEdges.contains(key) ? ", color=blue, penwidth=3" : "";
                    int c = residual.getResidualCapacity(e.from(), e.to());
                    pw.println("  " + e.from().getId() + " -> " + e.to().getId() +
                            " [label=\"" + c + "\"" + style + "];");
                }
                pw.println("}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
