import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * The Main class for Wordle solver app.
 * 
 * @author Jayden Webb
 * @version 1.0
 */
public class Main {
  
  /**
   * Main function - entry point to program. Implemented for you.
   * 
   * @param args - command line arguments
   */
  public static void main(String[] args) {
    // initialize and get all words
    
    int attempts = 0;
    ArrayList<Double> frequencies = getFrequencies();
    ArrayList<String> words = getWords();
    //words = filterWords(words, frequencies, 0.0);

    // produce initial guess and retrieve feedback
    String guess = initialGuess(new ArrayList<String>(words));
    words.remove(guess);
    System.out.println(words);
    char[] responses = guessHandler(guess);

    // subsequent guess loop
    while (!checkCorrect(responses) && attempts < 6) {
      attempts++;
      System.out.println("Attempts remaining: " + (6 - attempts) + ".");
      words = removeWords(new ArrayList<String>(words), guess, responses);
      System.out.println(words);
      guess = nextGuess(words);
      words.remove(guess);
      responses = guessHandler(guess);
    }

    // check to see if correct or not
    if (checkCorrect(responses))
      System.out.println("\nGreat job program!");
    else
      System.out.println("\nBad job program!");
  }

  public static ArrayList<Double> getFrequencies(){
    Path freqPath = Path.of("words_freqs.csv");
    String freqString = null;
    try {
      freqString = Files.readString(freqPath);
    } catch (IOException e){
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
  public static ArrayList<String> getWords() {
    //only returns words with non-zero frequencies
    Path wordsPath = Path.of("words.txt");
    String wordsString = null;
    try {
      wordsString = Files.readString(wordsPath);
    } catch (IOException e){
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

  public static ArrayList<String> filterWords(ArrayList<String> words, ArrayList<Double> frequencies, double frequencyThreshold){
    for (int wordIndex = 0; wordIndex < words.size();){
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
   * @param words - ArrayList of all possible words
   * @return String - resulting initial guess
   */
  public static String initialGuess(ArrayList<String> words) {
    
    /* no repeat letters
    at least 2 vowels
    choose random */

    Random rnd = new Random();
    for (int wordIndex = 0; wordIndex < words.size();) { // retrieves word
      String vowels = "aeiou";
      String testingWord = words.get(wordIndex);
      int vowelsInWord = 0;
      for (int i = 0; i < testingWord.length(); i++) { // for each character
        if (vowels.contains(testingWord.substring(i, i+1))){
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
        if (i == 0) {continue;} // prevents range errors
        if (testingWord.substring(0, i).contains(testingWord.substring(i, i+1))) {// removes words with duplicate characters
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
    return guess;
  }

  /**
   * Prompt/keyboard input handler for retrieving info from user.
   * 
   * @param guess - current guess
   * @return char[] - array of responses (either g/y/n) for each letter
   */
  public static char[] guessHandler(String guess) {
    Scanner sc = new Scanner(System.in);
    System.out.println("\n\nGuess: " + guess);
    char[] responses = { 0, 0, 0, 0, 0 };

    for (int i = 0; i < 5; i++) {
      char letter = guess.charAt(i);
      System.out.print(letter + " (g/y/n): ");
      char hint;
      try {
        hint = sc.nextLine().trim().toLowerCase().charAt(0);
      } catch (StringIndexOutOfBoundsException e) {
        hint = 0;
      }
      while (hint != 'g' && hint != 'y' && hint != 'n') {
        System.out.println("Illegal response");
        System.out.print(letter + " (g/y/n): ");
        try {
          hint = sc.nextLine().trim().toLowerCase().charAt(0);
        } catch (StringIndexOutOfBoundsException e) {
          hint = 0;
        }
      }
      responses[i] = hint;
    }
    // sc.close();
    return responses;
  }

  /**
   * Determines if the computer guessed correctly if all responses were 'g'
   * 
   * @param responses - char[] of responses from the user's console
   * @return boolean - returns true if correct, false if incorrect
   */
  public static boolean checkCorrect(char[] responses) {
    for (char character : responses) {
      if (character != 'g') {return false;}
    }
    return true;
  }

  /**
   * Removes all illegal words based on user's response and wordle rules.
   * 
   * @param words     - ArrayList of prior valid words to be cleaned up
   * @param guess     - String word that was guessed
   * @param responses - char[] of responses from user's console (either g/y/n)
   * @return String ArrayList - cleaned up list of valid words
   */
  public static ArrayList<String> removeWords(ArrayList<String> words, String guess, char[] responses) {
    //this method determines the method of word removal based on each letter and response, one at a time.
    
    for (int i = 0; i < 5; i++){ // through each character
      char letter = guess.charAt(i);
      char response = responses[i];
      boolean continueEvaluation = true;
      for (int j = 0; j < 5; j++){ // searches for duplicate
        if (j == i) {continue;} //skips current letter
        if (letter == guess.charAt(j)) { // if duplicate found
          if ((response != 'n') != (responses[j] != 'n')){ //either both are gray or both are not gray
            if (response == 'n'){ // if this letter is the gray one
              for (int wordIndex = 0; wordIndex < words.size();){ // remove multiple
                String word = words.get(wordIndex);
                int instances = 0;
                for (int charIndex = 0; charIndex < 5; charIndex++){ // iterate through characters of word
                  if (word.charAt(charIndex) == letter) {instances++;} // if character is query letter
                }
                if (instances > 1){ // if multiple instances of query letter
                  words.remove(word);
                } else {
                  wordIndex++;
                }
              }
              for (int wordIndex = 0; wordIndex < words.size();){ // remove at position
                String word = words.get(wordIndex);
                if (word.charAt(i) == letter){ // if letter at specified position, remove it
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
      if (!continueEvaluation) {continue;} // skips if letter was handled by gray duplicate exception rule above
      if (response == 'g') {
        for (int wordIndex = 0; wordIndex < words.size();){
          String word = words.get(wordIndex);
          if (word.charAt(i) != letter){ // if letter at specified position, remove it
            words.remove(word);
          } else {
            wordIndex++;
          }
        }
      } else if (response == 'y') {
        for (int wordIndex = 0; wordIndex < words.size();){
          String word = words.get(wordIndex);
          if (word.charAt(i) == letter){ // if letter at specified position, remove it
            words.remove(word);
          } else {
            wordIndex++;
          }
        }
        for (int wordIndex = 0; wordIndex < words.size();){
          String word = words.get(wordIndex);
          if (word.indexOf(letter) == -1){ // word does not contain letter
            words.remove(word);
          } else {
            wordIndex++;
          }
        }
      } else if (response == 'n') {
        for (int wordIndex = 0; wordIndex < words.size();) { // remove words containing letter
          String word = words.get(wordIndex);
          if (word.indexOf(letter) != -1){
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
  public static String nextGuess(ArrayList<String> words) {
    Random rnd = new Random();
    String guess = words.get(rnd.nextInt(words.size()));
    return guess;
  }
}
