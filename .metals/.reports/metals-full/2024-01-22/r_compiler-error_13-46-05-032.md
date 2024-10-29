file://<WORKSPACE>/app/src/main/java/wordle/KeyBoardPanel.java
### java.util.NoSuchElementException: next on empty iterator

occurred in the presentation compiler.

action parameters:
uri: file://<WORKSPACE>/app/src/main/java/wordle/KeyBoardPanel.java
text:
```scala
package wordle;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class KeyBoardPanel extends JPanel {

    private static Color COLOR_USED = new Color(45,45,45);
    private static Color DEFAULT = new Color(100,100,100);
    private static Color COLOR_YELLOW = new Color(220,220,0);
    private static Color COLOR_GREEN = new Color(0, 180, 0);

    Model model;

    JPanel firstRow;
    JPanel secondRow;
    JPanel thirdRow;

    JPanel mainPanel;

    public KeyBoardPanel(Model model) {
        super(new GridLayout(3,1));
        
        this.model = model;

        firstRow = new JPanel(new FlowLayout());
        secondRow = new JPanel(new FlowLayout());
        thirdRow = new JPanel(new FlowLayout());

        firstRow.add(new Key("Q"));
        firstRow.add(new Key("W"));
        firstRow.add(new Key("E"));
        firstRow.add(new Key("R"));
        firstRow.add(new Key("T"));
        firstRow.add(new Key("Y"));
        firstRow.add(new Key("U"));
        firstRow.add(new Key("I"));
        firstRow.add(new Key("O"));
        firstRow.add(new Key("P"));

        secondRow.add(new Key("A"));
        secondRow.add(new Key("S"));
        secondRow.add(new Key("D"));
        secondRow.add(new Key("F"));
        secondRow.add(new Key("G"));
        secondRow.add(new Key("H"));
        secondRow.add(new Key("J"));
        secondRow.add(new Key("K"));
        secondRow.add(new Key("L"));

        thirdRow.add(new Key("|ENTER|"));
        thirdRow.add(new Key("Z"));
        thirdRow.add(new Key("X"));
        thirdRow.add(new Key("C"));
        thirdRow.add(new Key("V"));
        thirdRow.add(new Key("B"));
        thirdRow.add(new Key("N"));
        thirdRow.add(new Key("M"));
        thirdRow.add(new Key("|DELETE|"));

        firstRow.setBackground(DEFAULT);
        secondRow.setBackground(DEFAULT);
        thirdRow.setBackground(DEFAULT);

        add(java.awt.BorderLayout.NORTH, firstRow);
        add(java.awt.BorderLayout.CENTER, secondRow);
        add(java.awt.BorderLayout.SOUTH, thirdRow);

        for (Component c1 : getComponents()) {
            for (Component c2 : ((JPanel)c1).getComponents()) {

                Key key = (Key) c2;

                key.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) { 
                        model.pressed(((Key)c2).getTitle());
                        SwingUtilities.getWindowAncestor(KeyBoardPanel.this).requestFocus();
                    }
                });
            }
        }
    }


    public void updateKeyboard() {
        for (Component c : getComponents()) {
            JPanel jp = (JPanel) c;
            for (Component c2 : jp.getComponents()) {
                Key key = (Key) c2;
                if (key.getTitle().length() <= 1) {
                    int status = model.alphabetStatus.get(key.getTitle().charAt(0));

                    switch (status) {
                        case 2:
                            key.setBackground(COLOR_GREEN);
                            break;
                        case 1:
                            key.setBackground(COLOR_YELLOW);
                            break;
                        case 0:
                            key.setBackground(COLOR_USED);
                            break;
                        case -1:
                            key.setBackground(DEFAULT);
                            break;
                    }
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
	scala.meta.internal.pc.PcCollector.<init>(PcCollector.scala:45)
	scala.meta.internal.pc.PcSemanticTokensProvider$Collector$.<init>(PcSemanticTokensProvider.scala:61)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector$lzyINIT1(PcSemanticTokensProvider.scala:61)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector(PcSemanticTokensProvider.scala:61)
	scala.meta.internal.pc.PcSemanticTokensProvider.provide(PcSemanticTokensProvider.scala:90)
	scala.meta.internal.pc.ScalaPresentationCompiler.semanticTokens$$anonfun$1(ScalaPresentationCompiler.scala:99)
```
#### Short summary: 

java.util.NoSuchElementException: next on empty iterator