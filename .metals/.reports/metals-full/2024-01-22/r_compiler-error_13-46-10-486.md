file://<WORKSPACE>/app/src/main/java/wordle/WordlePanel.java
### java.util.NoSuchElementException: next on empty iterator

occurred in the presentation compiler.

action parameters:
uri: file://<WORKSPACE>/app/src/main/java/wordle/WordlePanel.java
text:
```scala
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
        frame.add(java.awt.BorderLayout.CENTER, outPanel);
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