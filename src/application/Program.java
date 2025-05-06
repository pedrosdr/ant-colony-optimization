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
        g.addNode(new Node(0, 40.0, 30.0));
        g.addNode(new Node(1, 220.0, 39.0));
        g.addNode(new Node(2, 420.0, 310.0));
        g.addNode(new Node(3, 620.0, 160.0));
        g.addNode(new Node(4, 170.0, 360.0));
        g.addNode(new Node(5, 73.0, 535.0));
        g.addNode(new Node(6, 245.0, 489.0));
        g.addNode(new Node(7, 270.0, 560.0));
        g.addNode(new Node(8, 920.0, 380.0));
        g.addNode(new Node(9, 50.0, 610.0));
        g.connectAll(0.1);

        AntColonyOptim model = new AntColonyOptim(g);
        model.setnAnts(20);
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
