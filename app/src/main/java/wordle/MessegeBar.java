package wordle;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;


public class MessegeBar extends JTextField {

    private static Color DEFAULT = new Color(100,100,100);
    private static Color TEXT_COLOR = Color.BLACK;

    Model model;

    public MessegeBar(Model model) {
        this.model = model;

        setHorizontalAlignment(JTextField.CENTER);
        setForeground(TEXT_COLOR);
        setBackground(DEFAULT);
        setBorder(new LineBorder(DEFAULT, 2));
        setFont(new Font("Arial", Font.BOLD, 30));

        setFocusable(false);
    }
    
}
