package wordle;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;

public class Box extends JButton {

    private String title;

    public Box(String title, int size) {
        super("");

        this.title = title;

        setOpaque(true);
        
        setFont(new Font("ComicSans", Font.BOLD, size));
        setPreferredSize(new Dimension(50, 50));
    }

    public String getTitle() {
        return title;
    }

    public boolean equals(String s) {
        return title.equals(s);
    }
    
}
