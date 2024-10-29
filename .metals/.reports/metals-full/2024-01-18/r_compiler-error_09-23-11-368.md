file://<WORKSPACE>/app/src/main/java/wordle/GUI.java
### java.util.NoSuchElementException: next on empty iterator

occurred in the presentation compiler.

action parameters:
offset: 2785
uri: file://<WORKSPACE>/app/src/main/java/wordle/GUI.java
text:
```scala
package wordle;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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


    //Very cluttery, but GUI takes care of all calls from the model, henceforth a lot of If-statements.
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
            messageBar.setText("Wor@@d Is To Short. Please Type A Full Word.");

        } else if (arg.equals("GAMEOVER")) {
            String lastCorrectWord = correctWord;
            boolean wasWin = model.wasWin();
            
            correctWord = model.correctWord;
            wordlePanel.resetWordle();
            wordlePanel.updateColorEachBox();
            currentBox = wordlePanel.getCurrentBox();
            keyBoard.updateKeyboard();

            
            Object text = "You lost, correct word was: " + lastCorrectWord;
            if (wasWin) {
                text = "Congrats, correct word was: " + lastCorrectWord;
            }

            Object[] options = {"Continue", "Definition"};
            int n = JOptionPane.showOptionDialog(this,text,"GAMEOVER", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (n == 1) {
                try {
                    //Link to Oxford English Dictionary
                    java.awt.Desktop.getDesktop().browse(new URI("https://www.oed.com/search/dictionary/?scope=Entries&q="+lastCorrectWord));
                } catch (IOException e1) {
                    messageBar.setText("Sorry, could not find definition.");
                } catch (URISyntaxException e1) {
                    messageBar.setText("Sorry, could not find definition.");
                }
            }


        }
    }
}

```



#### Error stacktrace:

```
scala.collection.Iterator$$anon$19.next(Iterator.scala:973)
	scala.collection.Iterator$$anon$19.next(Iterator.scala:971)
	scala.collection.mutable.MutationTracker$CheckedIterator.next(MutationTracker.scala:76)
	scala.collection.IterableOps.head(Iterable.scala:222)
	scala.collection.IterableOps.head$(Iterable.scala:222)
	scala.collection.AbstractIterable.head(Iterable.scala:933)
	dotty.tools.dotc.interactive.InteractiveDriver.run(InteractiveDriver.scala:168)
	scala.meta.internal.pc.MetalsDriver.run(MetalsDriver.scala:45)
	scala.meta.internal.pc.HoverProvider$.hover(HoverProvider.scala:34)
	scala.meta.internal.pc.ScalaPresentationCompiler.hover$$anonfun$1(ScalaPresentationCompiler.scala:342)
```
#### Short summary: 

java.util.NoSuchElementException: next on empty iterator