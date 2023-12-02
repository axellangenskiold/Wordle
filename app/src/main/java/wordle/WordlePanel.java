package wordle;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WordlePanel extends JPanel {

    private Model model;

    private static int FONT_SIZE = 20;

    private static Color COLOR_YELLOW = new Color(220,220,0);
    private static Color COLOR_GREEN = new Color(0, 180, 0);
    private static Color DEFAULT = new Color(100,100,100);

    public WordlePanel(Model model, char rows, int cols) {
        super(new GridLayout(6,5));
        this.model = model;

        for (char c = 'A'; c <= rows; c++) {
            for (int i = 1; i <= cols; i++) {
            
                Box box = new Box(String.valueOf(c) + i, FONT_SIZE);

                box.setBackground(DEFAULT);
                box.setFocusable(false);
                box.setOpaque(true);
                box.setBorderPainted(true);
                add(box);
            }
        }

    } 

    public void addToFrame(JFrame frame) {
        JPanel outPanel = new JPanel();
        outPanel.setBackground(DEFAULT);
        outPanel.add(java.awt.BorderLayout.CENTER, this);
        frame.add(outPanel);
    }

    public Box getCurrentBox() {
        Box retBox = (Box) getComponent(0);
        for (Component c : getComponents()) {
            Box box = (Box) c;
            if (box.equals(model.getCurrentLetter())) {
                retBox = box;
            }
        }
        return retBox;
    }

    public void updateColorEachBox() {
        for (Component c : this.getComponents()) {
            Box box = (Box) c;
            setColor(model.getStatus(box.getTitle()), box);
        }
    }

    public void setColor(int i, Box box) {
        switch (i) {
            case 2:
                box.setBackground(COLOR_GREEN);
                return;
            case 1:
                box.setBackground(COLOR_YELLOW);
                return;
            default:
                box.setBackground(DEFAULT);
        }
    }

    public void resetWordle() {
        for (Component c : this.getComponents()) {
            Box box = (Box) c;
            box.setText("");
        }
    }
}
