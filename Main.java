/**
 * The Main entrypoint and game loop for Wordle Solver app.
 * 
 * @author Jayden Webb
 * @version 0.2
 */

public class Main {

  /**
   * Main function - entry point to program.
   * Creates objects for the input handler and guess generator, getting words and
   * frequencies in the process.
   * 
   * @param args - command line arguments
   */
  public static void main(String[] args) {

    System.out.println("Welcome to the Wordle Solver!\nCreated by Jayden Webb, December 2022");

    int attempts = 0;
    String guess;
    char[] responses;

    // Game Loop

    while (attempts < 6) {
      System.out.println("Guesses remaining: " + (6 - attempts) + ".");
      // Assign word from word list to guess
      guess = Handler.nextGuess(attempts);
      // Grab Wordle Feedback for the given guess
      responses = Handler.getFeedback(guess);
      if ("ggggg".equals(String.valueOf(responses))) {// stops execution if the guess was correct
        System.out.println("\nGreat job program!");
        break;
      }
      // Use feedback to eliminate illegal words from word list
      Brain.removeWords(guess, responses);
      attempts++;
    }

    if (attempts >= 6) { // output failure message if the correct guess was not reached
      System.out.println("\nBad job program!");
    }

    Handler.closeScanner();
  }

}
