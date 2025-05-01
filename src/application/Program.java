package application;

import UI.DrawingPanel;
import UI.MainFrame;
import entities.Edge;

public class Program {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();

        DrawingPanel panel = new DrawingPanel();
        panel.addDrawable(new Edge(0, 1, 20.0, 200.0));
        frame.add(panel);

        frame.setVisible(true);
    }
}
