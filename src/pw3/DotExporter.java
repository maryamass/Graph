package pw3;

import m1graphs2025.*;
import java.io.*;
import java.util.*;

public class DotExporter {

    public static void exportFlow(FLowNetwork network, Flow flow, int step, int value, String prefix) {
        try (PrintWriter pw = new PrintWriter(new File(prefix + "_flow" + step + ".gv"))) {
            pw.println("digraph flow" + step + " {");
            pw.println("rankdir=\"LR\"");
            pw.println("label=\"(" + step + ") Flow. Value: " + value + "\"");

            for (Edge e : network.getEdges()) {
                Node u = e.from(), v = e.to();
                int f = flow.getFlow(u, v);
                int c = network.getCapacity(u, v);
                pw.println("  " + u.getLabel() + " -> " + v.getLabel() + " [label=\"" + f + "/" + c + "\"];");
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
                pw.println("  " + e.from().getLabel() + " -> " + e.to().getLabel() +
                        " [label=" + residual.getResidualCapacity(e.from(), e.to()) + ", " + color + "];");
            }

            pw.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
