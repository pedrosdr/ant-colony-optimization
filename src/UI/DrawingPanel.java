package UI;

import entities.Conversor;
import entities.IDrawable;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class DrawingPanel extends JPanel {
    // fields
    private HashSet<IDrawable> drawables = new HashSet<>();

    // constructors
    public DrawingPanel() {
        super();
        setBackground(Color.white);
    }

    // methods
    public void addDrawable(IDrawable drawable) {
        drawables.add(drawable);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D gd = (Graphics2D) g;
        gd.drawOval(20, 20, 30, 30);
        Conversor conversor = new Conversor(getHeight());

        for(IDrawable drawable : drawables) {
            gd.setStroke(new BasicStroke(3.0f));
            gd.setColor(new Color(255, 0, 0, 20));
            drawable.draw(gd, conversor);
        }
    }
}
