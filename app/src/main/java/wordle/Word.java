package wordle;
import java.util.Arrays;

public class Word {

    private String[] word = {"", "", "", "", ""};
    private int[] status = {0, 0, 0, 0, 0};
    private boolean isUsed;
    boolean isCorrect;

    private FiveLetterWord fiveLetterWord;
    private String correctWord;


    public Word(FiveLetterWord fiveLetterWord, String correctWord) {
        isUsed = false;
        isCorrect = false;

        this.fiveLetterWord = fiveLetterWord;
        this.correctWord = correctWord;

    }

    public void add(int currentLetter, String letterToAdd) {
        word[currentLetter] = letterToAdd;
    }

    public boolean isFull() {
        return !word[4].equals("");
    }

    public boolean isEmpty() {
        return word[0].equals("");
    }

    public boolean contains(String letter) {
        return Arrays.asList(word).contains(letter);
    }

    public int delete() {
        if (isFull()) {
            word[4] = "";
            return 4;
        } else {
            return delete(4, 3);
        }
    }

    public int delete(int prev, int curr) {
        if (word[prev].equals("") && !word[curr].equals("")) {
            word[curr] = "";
            return curr;
        } else {
            return delete(prev -1, curr -1);
        }

    }

    public String getWord() {
        StringBuilder sb = new StringBuilder();

        for (String letter : word) {
            sb.append(letter);
        }

        return sb.toString();
    }

    public boolean isWord() {
        if (isFull()) {
            return fiveLetterWord.isWord(getWord());
        }
        return false;
    }

    public String getLetter(int i) {
        return word[i];
    }

    public void execute() {
        for (int i = 0; i < word.length; i++) {
            if (word[i].equals(String.valueOf(correctWord.charAt(i)))) {
                status[i] = 2;
            } else if (correctWord.contains(word[i])) {
                status[i] = 1;
            }
        }
        isUsed = !isUsed;

        if (getWord().equals(correctWord)) {
            isCorrect = !isCorrect;
        }
    }

    public int getStatus(int i) {
        return status[i];
    }

    public boolean hasBeenExecuted() {
        return isUsed;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < word.length; i++) {
            sb.append(word[i] + " (" + status[i] + ") ");
        }

        return sb.toString();
    }



    
}
