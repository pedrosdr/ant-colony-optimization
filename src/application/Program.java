package application;

import UI.DrawingPanel;
import UI.MainFrame;
import entities.*;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        MainFrame frame = new MainFrame();
        DrawingPanel panel = new DrawingPanel();
        frame.add(panel);
        frame.setVisible(true);

        Graph g = new Graph();
        g.addNode(new Node(0, 20.0, 20.0));
        g.addNode(new Node(1, 200.0, 29.0));
        g.addNode(new Node(2, 400.0, 300.0));
        g.addNode(new Node(3, 600.0, 150.0));
        g.addNode(new Node(4, 150.0, 350.0));
        g.addNode(new Node(5, 52.0, 525.0));
        g.addNode(new Node(6, 225.0, 479.0));
        g.addNode(new Node(7, 250.0, 550.0));
        g.addNode(new Node(8, 900.0, 370.0));
        g.connectAll(0.1);

        AntColonyOptim model = new AntColonyOptim(g);
        model.setRunnable(() -> panel.draw(g));
        panel.draw(g);

        Thread.sleep(50);

        for(int i = 0; i < 10000; i++) {
            model.cycle();

            Thread.sleep(100);
            System.out.println(String.format("Epoch %d", i+1));
        }
        panel.draw(g);
    }
}
