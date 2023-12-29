package wordle;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class Model extends Observable{
    
    private Word[] lines;

    private Word currentWord;
    private int currentLetter;
    private boolean wasWin;
    private FiveLetterWord fiveLetterWord;
    
    String correctWord;
    Map<Character, Integer> alphabetStatus;

    
    
    public Model() throws FileNotFoundException {
        lines = new Word[6];
        fiveLetterWord = new FiveLetterWord("/Users/axellangenskiold/Documents/personal-projects/wordle/app/src/fiveLetterWords2.txt");
        correctWord = fiveLetterWord.getRandomWord().toUpperCase();
        alphabetStatus = new HashMap<>();
        alphabetSetup();

        for (int i = 0; i < lines.length; i++) {
            lines[i] = new Word(fiveLetterWord, correctWord);
        }

        currentWord = lines[0];
        currentLetter = 0;
        wasWin = false;
    }

    //returns the word at index i
    public String getWord(int i) {
        return lines[i].getWord();
    }

    //returns the a String of the letters in current word
    public String getWord() {
        return currentWord.getWord();
    }

    //when key is pressed, this function choses what to do
    public void pressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            delete();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            execute();

        } else if (e.getKeyCode() >= KeyEvent.VK_A && e.getKeyCode() <= KeyEvent.VK_Z) {
            addLetter(Character.toString(e.getKeyChar()));
        }

    }

    //when key is pressed, this function choses what to do
    public void pressed(String s) {

        char c = s.toCharArray()[0];
        System.out.println("beginning of pressed" + c);

        
        if (s.equals("|DELETE|")) {
            delete();
        } else if (s.equals("|ENTER|")) {
            execute();
        } else if (c >= 'A' && c <= 'Z') {
            addLetter(Character.toString(c));
        }

    }

    //adds letter s to a word if Word isn't full
    //notifies the GUI
    private void addLetter(String s) {
        if(!currentWord.isFull()) {
            currentWord.add(currentLetter, s.toUpperCase());
            printMatrix();
            nextLetter();
            setChanged();
            notifyObservers("addedLetter");
        }
    }

    //if word isn't full and it is a word, executes word
    //notifies GUI
    private void execute() {
        if (currentWord.isFull()) {
            if (currentWord.isWord()) {
                currentWord.execute();
                updateAlphabet();
                printMatrix();
                nextWord();
                setChanged();
                if (didntGuessCorrectWord()) {
                    notifyObservers("executed");
                    System.out.print("                 EXECUTED SUCCESFULLY");
                } else {
                    gameOver();
                }
            } else {
                setChanged();
                notifyObservers("notWord");
            }
        } else {
            setChanged();
            notifyObservers("tooShort");
        }
    }

    //deletes letter if currentWord isn't empty
    private void delete() {
        if (!currentWord.isEmpty()) {
            currentLetter = currentWord.delete();
            printMatrix();
            setChanged();
            notifyObservers("deletedLetter");
        }
    }

    //resets game to starting state
    //notifies GUI to update
    private void gameOver() {
        reset();
        notifyObservers("GAMEOVER");

        wasWin = false;
    }

    //if currentWord isn't full, updates currentLetter to next letter
    private void nextLetter() {
        if (!currentWord.isFull()) {
            currentLetter += 1;
        }
    }

    //returns wether guess was or wasn't correct
    private boolean didntGuessCorrectWord() {
        boolean check = true;
        for (Word w : lines) {
            check = check && !w.isCorrect;
        }
        if (!check) {
            wasWin = true;
        }
        return check;
    }

    //if currentWord has been executed and player hasn't run out of guesses,
    //updates to next Word
    private void nextWord() {
        if (currentWord.hasBeenExecuted() && !currentWord.equals(lines[5])) {
            int index = -1;
            for (int i = 0; i < 6; i++) {
                if (lines[i].equals(currentWord)) {
                    index = i+1;
                }
            }
            currentWord = lines[index];
            currentLetter = 0;
        } else {
            wasWin = currentWord.isCorrect;
            setChanged();
            gameOver();
        }
    }

    //for GUI to see what java.awt.Color to give each Box
    //-49 to compensate for ACSCII
    public int getStatus(String s) {
        char[] tuple = s.toCharArray();

        switch (tuple[0]) {
            case 'A':
                return lines[0].getStatus(tuple[1]-49);
            case 'B':
                return lines[1].getStatus(tuple[1]-49);
            case 'C':
                return lines[2].getStatus(tuple[1]-49);
            case 'D':
                return lines[3].getStatus(tuple[1]-49);
            case 'E':
                return lines[4].getStatus(tuple[1]-49);
            case 'F':
                return lines[5].getStatus(tuple[1]-49);
            default:
                return 0;
            
        }
    }

    //returns the title for the current letter
    //return looks like: "A2" or "C5"
    public String getCurrentLetter() {
        int index = Arrays.asList(lines).indexOf(currentWord);

        switch (index) {
            case 0:
                return 'A' + Integer.toString(currentLetter+1);
            case 1:
                return 'B' + Integer.toString(currentLetter+1);
            case 2:
                return 'C' + Integer.toString(currentLetter+1);
            case 3:
                return 'D' + Integer.toString(currentLetter+1);
            case 4:
                return 'E' + Integer.toString(currentLetter+1);
            case 5:
                return 'F' + Integer.toString(currentLetter+1);
            default:
                return "A1";
        }
    }

    //returns letter from the currentWord
    public String getLetter(String s) {
        int index = Character.getNumericValue(s.charAt(1));
        return currentWord.getLetter(index-1);
    }

    //prints matrix
    public void printMatrix() {
        for (Word w : lines) {
            System.out.println(w);
        }
        System.out.println();
    }

    //sets up alphabet for GUI
    public void alphabetSetup() {
        for (char c = 'A'; c <= 'Z'; c++) {
            alphabetStatus.put(c, 0);
        }
    }

    //for GUI, if any letter in currentWord is in the correct word
    //the alphabet updates
    public void updateAlphabet() {
        for (int i = 0; i < correctWord.length(); i++) {
            char c = currentWord.getWord().charAt(i);
            if (correctWord.contains(Character.toString(c))) {
                alphabetStatus.put(c, 2);
            } else {
                alphabetStatus.put(c, 1);
            }
        }
    }

    //returns if it was a win or not
    public boolean wasWin() {
        return wasWin;
    }

    //resets the model to starting state
    public void reset() {
        correctWord = fiveLetterWord.getRandomWord();
        alphabetStatus.clear();
        alphabetSetup();
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new Word(fiveLetterWord, correctWord);
        }
        currentWord = lines[0];
        currentLetter = 0;
    }
}
