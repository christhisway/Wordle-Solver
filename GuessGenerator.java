import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Contains the words and their frequencies, and generates most optimal guess.
 * 
 * @since 1.1
 */

public class GuessGenerator {
    private static ArrayList<Double> frequencies;
    private static ArrayList<String> words;
    /**
     * The main function grabs all the words and frequencies, filters out the
     * 0-frequency words, and places them in an ArrayList.
     * 
     * @param args - command line arguments; not necessary.
     */
    public GuessGenerator(){
        frequencies = getFrequencies();
        words = getWords();
        words = filterWords(0.0);
    }
    
    private static ArrayList<Double> getFrequencies() {
        Path freqPath = Path.of("words_freqs.csv");
        String freqString = null;
        try {
            freqString = Files.readString(freqPath);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
        String[] freqArray = freqString.split("\n");
        ArrayList<Double> freqArrayList = new ArrayList<Double>();
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
    private static ArrayList<String> getWords() {
        // only returns words with non-zero frequencies
        Path wordsPath = Path.of("words.txt");
        String wordsString = null;
        try {
            wordsString = Files.readString(wordsPath);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
        String[] wordsArray = wordsString.split("\n");
        ArrayList<String> wordsArrayList = new ArrayList<String>();
        for (String word : wordsArray) {
            wordsArrayList.add(word);
        }
        return wordsArrayList;
    }

    private static ArrayList<String> filterWords(double frequencyThreshold) {
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

    /**
     * Method for determining initial guess.
     * 
     * @return String - resulting initial guess
     */
    public String initialGuess() {

        /*
         * no repeat letters
         * at least 2 vowels
         * choose random
         */

        Random rnd = new Random();
        for (int wordIndex = 0; wordIndex < words.size();) { // retrieves word
            String vowels = "aeiou";
            String testingWord = words.get(wordIndex);
            int vowelsInWord = 0;
            for (int i = 0; i < testingWord.length(); i++) { // for each character
                if (vowels.contains(testingWord.substring(i, i + 1))) {
                    vowelsInWord++;
                }
            }
            if (vowelsInWord < 2) { // makes sure vowels are different
                words.remove(testingWord);
            } else {
                wordIndex++;
            }
        }
        for (int wordIndex = 0; wordIndex < words.size();) { // retrieves word
            boolean wordContainsDuplicates = false;
            String testingWord = words.get(wordIndex);
            for (int i = 0; i < testingWord.length(); i++) { // for each character
                if (i == 0) {
                    continue;
                } // prevents range errors
                if (testingWord.substring(0, i).contains(testingWord.substring(i, i + 1))) {
                    // removes words with duplicate characters
                    wordContainsDuplicates = true;
                    break;
                }
            }
            if (wordContainsDuplicates) {
                words.remove(testingWord);
            } else {
                wordIndex++;
            }
        }
        String guess = words.get(rnd.nextInt(words.size()));
        words.remove(guess);
        return guess;
    }

    /**
     * Removes all illegal words based on user's response and wordle rules.
     * 
     * @param guess     - String word that was guessed
     * @param responses - char[] of responses from user's console (either g/y/n)
     * @return String ArrayList - cleaned up list of valid words
     */
    public ArrayList<String> removeWords(String guess, char[] responses) {
        // this method determines the method of word removal based on each letter and
        // response, one at a time.

        for (int i = 0; i < 5; i++) { // through each character
            char letter = guess.charAt(i);
            char response = responses[i];
            boolean continueEvaluation = true;
            for (int j = 0; j < 5; j++) { // searches for duplicate
                if (j == i) {
                    continue;
                } // skips current letter
                if (letter == guess.charAt(j)) { // if duplicate found
                    if ((response != 'n') != (responses[j] != 'n')) { // either both are gray or both are not gray
                        if (response == 'n') { // if this letter is the gray one
                            for (int wordIndex = 0; wordIndex < words.size();) { // remove multiple
                                String word = words.get(wordIndex);
                                int instances = 0;
                                for (int charIndex = 0; charIndex < 5; charIndex++) { // iterate through characters of
                                                                                      // word
                                    if (word.charAt(charIndex) == letter) {
                                        instances++;
                                    } // if character is query letter
                                }
                                if (instances > 1) { // if multiple instances of query letter
                                    words.remove(word);
                                } else {
                                    wordIndex++;
                                }
                            }
                            for (int wordIndex = 0; wordIndex < words.size();) { // remove at position
                                String word = words.get(wordIndex);
                                if (word.charAt(i) == letter) { // if letter at specified position, remove it
                                    words.remove(word);
                                } else {
                                    wordIndex++;
                                }
                            }
                            continueEvaluation = false;
                        }
                    }
                }
            }
            if (!continueEvaluation) {
                continue;
            } // skips if letter was handled by gray duplicate exception rule above
            if (response == 'g') {
                for (int wordIndex = 0; wordIndex < words.size();) {
                    String word = words.get(wordIndex);
                    if (word.charAt(i) != letter) { // if letter at specified position, remove it
                        words.remove(word);
                    } else {
                        wordIndex++;
                    }
                }
            } else if (response == 'y') {
                for (int wordIndex = 0; wordIndex < words.size();) {
                    String word = words.get(wordIndex);
                    if (word.charAt(i) == letter) { // if letter at specified position, remove it
                        words.remove(word);
                    } else {
                        wordIndex++;
                    }
                }
                for (int wordIndex = 0; wordIndex < words.size();) {
                    String word = words.get(wordIndex);
                    if (word.indexOf(letter) == -1) { // word does not contain letter
                        words.remove(word);
                    } else {
                        wordIndex++;
                    }
                }
            } else if (response == 'n') {
                for (int wordIndex = 0; wordIndex < words.size();) { // remove words containing letter
                    String word = words.get(wordIndex);
                    if (word.indexOf(letter) != -1) {
                        words.remove(word);
                    } else {
                        wordIndex++;
                    }
                }
            }
        }
        return words;
    }

    /**
     * Criteria for determining next optimal guess. (Optional)
     * 
     * @param words - list of possible words
     * @return String - next guess
     */
    public String nextGuess() {
        Random rnd = new Random();
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
