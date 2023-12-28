import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/**
 * Contains the words and their frequencies, and generates each guess.
 * 
 * @since 0.2
 */

public class Brain {
    private static Random rnd = new Random();
    // private static ArrayList<Double> frequencies = getFrequencies();
    private static ArrayList<String> words = getWords("solutions.csv");
    public static ArrayList<String> validGuesses = getWords("words.txt");
    

    /**
     * Reads file of word frequencies and puts them into a ArrayList.
     * 
     * @return Double ArrayList - all frequencies of words.
     */
    private static ArrayList<Double> getFrequencies() {
        Path freqPath = Path.of("solutions.csv");
        String freqString = null;
        try {
            freqString = Files.readString(freqPath);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
        String[] freqArray = freqString.split("\n");
        ArrayList<Double> freqArrayList = new ArrayList<>();
        for (String freqLine : freqArray) {
            Double frequency = Double.parseDouble(freqLine.substring(6));
            freqArrayList.add(frequency);
        }
        return freqArrayList;
    }

    /**
     * Retrieves all possible words from the file.
     * 
     * @return String ArrayList - all possible words
     */
    private static ArrayList<String> getWords(String filePath) {
        // only returns words with non-zero frequencies
        Path wordsPath = Path.of(filePath);
        String wordsString = null;
        try {
            wordsString = Files.readString(wordsPath);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
        String[] wordsArray = wordsString.split("\n");
        ArrayList<String> wordsArrayList = new ArrayList<>();
        for (String wordsLine : wordsArray){
            String word = wordsLine.substring(0, 5);
            wordsArrayList.add(word);
        }
        return wordsArrayList;
    }

    /**
     * Removes all words under a specified frequency (generally set to 0.0) from the
     * word list.
     * 
     * @param words              ArrayList<String> of words to be filtered
     * @param frequencyThreshold Double value, at or below which all words should be
     *                           removed from words
     * @return Modified String ArrayList without the unfrequent words
     */
    private static ArrayList<String> filterWords(ArrayList<String> words, ArrayList<Double> frequencies,
            double frequencyThreshold) {
        for (int wordIndex = 0; wordIndex < words.size();) {
            Double wordFrequency = frequencies.get(wordIndex);
            if (wordFrequency <= frequencyThreshold) {
                words.remove(wordIndex);
                frequencies.remove(wordIndex);
            } else {
                wordIndex++;
            }
        }
        return words;
    }

    public static ArrayList<String> removeWords(String guess, char[] responses) {
        Iterator<String> iterator = words.iterator();
        int letterIndex;
        while (iterator.hasNext()) {
            String word = iterator.next();

            for (int i = 0; i < 5; i++) {
                char letter = guess.charAt(i);
                char response = responses[i];
                char wordChar = word.charAt(i);

                if (response == 'g' && wordChar != letter) {
                    // if mismatched green letter
                    iterator.remove();
                    break;
                } else if (response == 'y' && (wordChar == letter || word.indexOf(letter) == -1)) {
                    // if matched yellow letter OR absence of yellow letter
                    iterator.remove();
                    break;
                } else if (response == 'n') {
                    if (wordChar == letter) {
                        // if matched gray letter
                        iterator.remove();
                        break;
                    } else if (word.indexOf(letter) != -1) { // if gray letter somewhere in word
                        letterIndex = word.indexOf(letter);
                        // remove unless letter position is locked OR there is a yellow letter somewhere
                        boolean containsYellowInstances = false;
                        int index = 0;
                        while (index < 5) {
                            if ((guess.charAt(index) == letter) && (responses[index] == 'y') && letterIndex < i) {
                                containsYellowInstances = true;
                            }
                            index++;
                        }
                        //add: if the locked position is BEFORE gray letter, remove it
                        if (!(responses[letterIndex] == 'g' && guess.charAt(i) == letter) 
                            && !(containsYellowInstances)) {
                            iterator.remove();
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("There are " + words.size() + " possible answers remaining.");
        return words;
    }
    /**
     * Criteria for determining next optimal guess.
     * 
     * @return String - next guess
     */
    public static String nextGuess(int attempts) {
        if (attempts == 0) return "salet";
        try {
            String guess = words.get(rnd.nextInt(words.size()));
            words.remove(guess);
            return guess;
        } catch (IllegalArgumentException e) {
            System.out.println("Error PICNIC: The word list has no more valid words.");
            System.exit(0);
            return "";
        }
    }

}
