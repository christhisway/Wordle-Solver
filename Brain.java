import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/**
 * Contains all functions that deal with filtering the words list and generating guesses.
 * The list of all possible words and the list of all possible solutions are kept in this class.
 * 
 * @since 0.2
 */

public class Brain {
    private static Random rnd = new Random();
    private static ArrayList<String> solutions = getWords("solutions.csv");
    public static ArrayList<String> validGuesses = getWords("words.txt");
    

    /**
     * Retrieves all possible words from the given file and places them into an ArrayList.
     * 
     * @param filePath the path of the file to be read: either solutions.csv or words.txt.
     * @return String ArrayList - all possible words
     * 
     */
    private static ArrayList<String> getWords(String filePath) {
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
     * Filters through the solution list and removes any invalid guesses using the most recent guess and Wordle's feedback.
     * 
     * @param guess - the guess the user entered into the Wordle
     * @param responses the color of each letter given in response to the user's guess
     */
    public static void removeWords(String guess, char[] responses) {
        Iterator<String> iterator = solutions.iterator();
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
        System.out.println("There are " + solutions.size() + " possible answers remaining.");
        System.out.println(solutions);
    }


    /**
     * Criteria for determining next optimal guess. It will always return "SALET" for the first guess, as this has been experimentally proven to be the most effective first guess for programs. In all subsequent guesses, it returns a random word from the updated solutions list.
     * 
     * @param attempts the number of attempts the user has already used.
     * @return String - the word determined to be the best next guess.
     */
    public static String nextGuess(int attempts) {
        if (attempts == 0) return "salet";
        try {
            String guess = solutions.get(rnd.nextInt(solutions.size()));
            solutions.remove(guess);
            return guess;
        } catch (IllegalArgumentException e) {
            System.out.println("Error PICNIC: The word list has no more valid words.");
            System.exit(0);
            return "";
        }
    }

}
