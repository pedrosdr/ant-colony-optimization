package UI;

import entities.Conversor;
import entities.Graph;
import entities.IDrawable;
import entities.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

public class DrawingPanel extends JPanel {
    // fields
    private IDrawable drawable;
    private Graph g = new Graph();
    private int nodeKey = 0;

    // constructors
    public DrawingPanel() {
        super();
        clear();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                g.addNode(
                        new Node(nodeKey, e.getX(), e.getY())
                );
                nodeKey++;
                g.connectAll(0.1);
                draw(g);
            }
        });
    }

    // properties
    public Graph getGraph() {
        return g;
    }

    public void setGraph(Graph g) {
        this.g = g;
    }

    // methods
    public void draw(IDrawable drawable) {
        this.drawable = drawable;
        repaint();
    }

    public void clear() {
        draw((gd) -> gd.clearRect(0, 0, getWidth(), getHeight()));
        g.clear();
        nodeKey = 0;
    }

    @Override
    public void paint(Graphics g) {
        if(drawable == null) return;

        Graphics2D gd = (Graphics2D) g;
        gd.clearRect(0, 0, getWidth(), getHeight());
        drawable.draw(gd);
    }
}
