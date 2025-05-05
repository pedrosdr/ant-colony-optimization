package UI;

import entities.Conversor;
import entities.IDrawable;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class DrawingPanel extends JPanel {
    // fields
    private IDrawable drawable;

    // constructors
    public DrawingPanel() {
        super();
        setBackground(Color.white);
    }

    // methods
    public void draw(IDrawable drawable) {
        this.drawable = drawable;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        if(drawable == null) return;

        Graphics2D gd = (Graphics2D) g;
        gd.clearRect(0, 0, getWidth(), getHeight());
        drawable.draw(gd, new Conversor(getHeight()));
    }
}
