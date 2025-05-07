package UI;

import entities.AntColonyOptim;
import entities.Graph;
import entities.Node;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    private DrawingPanel panel = new DrawingPanel();
    private final JLabel label = new JLabel();
    private final JTextField tfNAnts = new JTextField("20");
    private final JTextField tfAlpha = new JTextField("1.0");
    private final JTextField tfBeta = new JTextField("1.0");
    private final JTextField tfEvaporation = new JTextField("0.1");
    private final JTextField tfQ = new JTextField("200.0");
    private Thread thread;

    // constructors
    public MainFrame() {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        panel.setBounds(0, 0, getWidth() - 130, getHeight() - 100);
        add(panel);

        tfNAnts.setBounds(
                getWidth() - 80,
                10, 60, 20
        );
        add(tfNAnts);
        JLabel lbl = new JLabel("Ants");
        lbl.setBounds(panel.getWidth()+10, tfNAnts.getY(), 50, 20);
        add(lbl);

        tfAlpha.setBounds(
                getWidth() - 80,
                10 + tfNAnts.getHeight() + 10,
                60, 20
        );
        add(tfAlpha);
        lbl = new JLabel("α");
        lbl.setBounds(panel.getWidth()+10, tfAlpha.getY(), 50, 20);
        add(lbl);

        tfBeta.setBounds(
                getWidth() - 80,
                tfAlpha.getY() + tfAlpha.getHeight() + 10,
                60, 20
        );
        add(tfBeta);
        lbl = new JLabel("β");
        lbl.setBounds(panel.getWidth()+10, tfBeta.getY(), 50, 20);
        add(lbl);

        tfEvaporation.setBounds(
                getWidth() - 80,
                tfBeta.getY() + tfBeta.getHeight() + 10,
                60, 20
        );
        add(tfEvaporation);
        lbl = new JLabel("p");
        lbl.setBounds(panel.getWidth()+10, tfEvaporation.getY(), 50, 20);
        add(lbl);

        tfQ.setBounds(
                getWidth() - 80,
                tfEvaporation.getY() + tfEvaporation.getHeight() + 10,
                60, 20
        );
        add(tfQ);
        lbl = new JLabel("Q");
        lbl.setBounds(panel.getWidth()+10, tfQ.getY(), 50, 20);
        add(lbl);

        JButton btnStart = new JButton("Iniciar");
        btnStart.addActionListener(this::btnStartClick);
        btnStart.setBounds(10, getHeight() - 90, 200, 50);
        add(btnStart);

        JButton btnClear = new JButton("Limpar");
        btnClear.addActionListener(this::btnClearClick);
        btnClear.setBounds(
                btnStart.getX() + btnStart.getWidth() + 10,
                getHeight() - 90,
                200, 50
        );
        add(btnClear);

        label.setBounds(
                btnClear.getX() + btnClear.getWidth() + 10,
                panel.getHeight() + 10,
                200, 50
        );
        add(label);
    }

    // properties
    public DrawingPanel getPanel() {
        return panel;
    }

    public void setPanel(DrawingPanel panel) {
        this.panel = panel;
    }

    // methods
    private void killThread() {
        if(thread != null) {
            thread.interrupt();
            thread = null;
        }
    }
    private void btnStartClick(ActionEvent e) {
        Graph g = panel.getGraph();
        g.reset();
        killThread();

        thread = new Thread(()-> {
            AntColonyOptim model = new AntColonyOptim(g);
            model.setAlpha(Double.parseDouble(tfAlpha.getText()));
            model.setBeta(Double.parseDouble(tfBeta.getText()));
            model.setnAnts(Integer.parseInt(tfNAnts.getText()));
            model.setEvaporationRate(Double.parseDouble(tfEvaporation.getText()));
            model.setDeltaConstant(Double.parseDouble(tfQ.getText()));
            model.setRunnable(() -> panel.draw(g));

            for(int i = 0; i < 10000; i++) {
                try {
                    model.cycle();
                } catch (IllegalArgumentException ex) {
                    return;
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    return;
                }
                panel.draw(g);
                label.setText(String.format("Época %d", i+1));
            }
        });

        thread.start();
    }

    private void btnClearClick(ActionEvent e) {
        killThread();
        panel.clear();
    }
}
