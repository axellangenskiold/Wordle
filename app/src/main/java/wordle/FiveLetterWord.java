package wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class FiveLetterWord {

    Set<String> wordSet;

    public FiveLetterWord (String path) throws FileNotFoundException {
        File file = new File(path);

        Scanner scan = new Scanner(file);

        wordSet = new HashSet<String>();

        while (scan.hasNextLine()) {
            wordSet.add(scan.nextLine().toUpperCase());
        }

        scan.close();
    }

    public boolean isWord(String word) {
        return wordSet.contains(word);
    }


    public String getRandomWord() {
        int randomIndex = new Random().nextInt(wordSet.size());

        return (String) wordSet.toArray()[randomIndex];

    }
    
}
