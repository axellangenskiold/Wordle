package wordle;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("deprecation")
public class GUI extends JFrame implements Observer {
    private Model model;

    private Box currentBox;
    private WordlePanel wordlePanel;
    private KeyBoardPanel keyBoard;
    private String correctWord;
    private MessegeBar messageBar;

    private static char ROWS = 'F';
    private static int COLS = 5;
    

    public GUI() throws FileNotFoundException {
        super("wordle");
        this.model = new Model();

        keyBoard = new KeyBoardPanel(model);
        wordlePanel = new WordlePanel(model, ROWS, COLS);
        messageBar = new MessegeBar(model);


        add(java.awt.BorderLayout.NORTH, messageBar);
        wordlePanel.addToFrame(this);

        currentBox = wordlePanel.getCurrentBox();
        correctWord = model.correctWord;
        
        add(java.awt.BorderLayout.SOUTH, keyBoard);



        this.addKeyListener(new KeyAdapter() {
            @Override 
            public void keyPressed(KeyEvent e) {
                model.pressed(e);
            }
        });

        model.addObserver(this);
        
        
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
        requestFocus();
    }

    public void changeCurrentBox() {
        currentBox = wordlePanel.getCurrentBox();
    }


    public static void main(String[] args) throws FileNotFoundException {
        new GUI();
    }


    @Override
    public void update(Observable o, Object arg) {
        messageBar.setText("");
        if (arg.equals("addedLetter")) {
            currentBox.setText(model.getLetter(currentBox.getTitle()));
            currentBox = wordlePanel.getCurrentBox();



        } else if (arg.equals("deletedLetter")) {
            currentBox = wordlePanel.getCurrentBox();
            currentBox.setText(model.getLetter(currentBox.getTitle()));
            System.out.println(currentBox.getTitle());



        } else if (arg.equals("executed")) {
            currentBox = wordlePanel.getCurrentBox();
            wordlePanel.updateColorEachBox();
            keyBoard.updateKeyboard();



        } else if (arg.equals("notWord")) {
            messageBar.setText(model.getWord() + " Is Not A Word. Please Try Another One.");



        } else if (arg.equals("tooShort")) {
            messageBar.setText("Word Is To Short. Please Type A Full Word.");



        } else if (arg.equals("GAMEOVER")) {
        String lastCorrectWord = correctWord;
        boolean wasWin = model.wasWin();
        
        correctWord = model.correctWord;
        wordlePanel.resetWordle();
        wordlePanel.updateColorEachBox();
        currentBox = wordlePanel.getCurrentBox();
        keyBoard.updateKeyboard();

        if (wasWin) {
            JOptionPane.showMessageDialog(this, "Congrats, correct word was: " + lastCorrectWord);
        } else {
            JOptionPane.showMessageDialog(this, "You lost, correct word was: " + lastCorrectWord);
        }
        
        }
    }
}
