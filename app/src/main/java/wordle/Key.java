package wordle;


import java.awt.Color;

import javax.swing.JButton;

public class Key extends JButton{

    String s;

    private static Color DEFAULT = new Color(100,100,100);


    public Key(String s) {
        super(s.toUpperCase());
        this.s = s;

        setBackground(DEFAULT);
        setFocusable(false);
        setOpaque(true);
        setBorderPainted(true);
    }


    public boolean equals(String string) {
        return s.equals(string);
    }

    public String getTitle() {
        return s;
    }
    


}
