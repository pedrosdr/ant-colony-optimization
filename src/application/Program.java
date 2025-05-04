package application;

import UI.DrawingPanel;
import UI.MainFrame;
import entities.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
//        MainFrame frame = new MainFrame();
//
//        DrawingPanel panel = new DrawingPanel();
//        Edge edge = new Edge(new Node(0, 20.0, 20.0), new Node(1, 400.0, 400.0), 20.0);
//        panel.addDrawable(edge);
//        frame.add(panel);
//
//        frame.setVisible(true);

        Graph g = new Graph();
        g.addNode(new Node(0, 20.0, 20.0));
        g.addNode(new Node(1, 200.0, 29.0));
        g.addNode(new Node(2, 400.0, 300.0));
        g.addNode(new Node(3, 600.0, 150.0));
        g.addNode(new Node(4, 450.0, 350.0));
        g.addNode(new Node(5, 52.0, 525.0));
        g.addNode(new Node(6, 425.0, 479.0));
        g.addNode(new Node(7, 350.0, 350.0));
        g.addNode(new Node(8, 900.0, 370.0));
        g.connectAll(200.0);

        Ant ant = new Ant(g.getNodes().get(1), g);

        AntColonyOptim model = new AntColonyOptim(g, 1.0, 1.0, 3.0);

        model.cicle(ant);

        MainFrame frame = new MainFrame();

        DrawingPanel panel = new DrawingPanel();
        frame.add(panel);
        frame.setVisible(true);
        panel.draw(g);

        panel.draw(ant.getPath());
    }
}
