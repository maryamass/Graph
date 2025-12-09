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
            pw.println("digraph residual" + step + " {");
            pw.println("rankdir=\"LR\"");
            pw.println("label=\"(" + step + ") Residual graph. Flow: " + currentFlow + "\"");

            Set<String> pathEdges = new HashSet<>();
            if (path != null) {
                for (int i = 0; i < path.size() - 1; i++)
                    pathEdges.add(path.get(i).getLabel() + "->" + path.get(i + 1).getLabel());
            }

            for (Edge e : residual.getAllEdges()) {
                String key = e.from().getLabel() + "->" + e.to().getLabel();
                String color = pathEdges.contains(key) ? "color=\"blue\", penwidth=3" : "";
                //int f = flow.getFlow(e.from(), e.to());
                int c = residual.getResidualCapacity(e.from(), e.to());
                String style = pathEdges.contains(key) ? ", color=blue, penwidth=3" : "";
                pw.println("  " + e.from().getId() + " -> " + e.to().getId() +
                        " [label=\"" +  "/" + c + "\"" + style + "];");
            }

            pw.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
