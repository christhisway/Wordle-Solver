/**
 * The Main entrypoint and game loop for Wordle Solver app.
 * 
 * @author Jayden Webb
 * @version 1.1
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
    // generatorInitialize();
    // Handler Handler = new Handler();

    // produce initial guess and retrieve feedback
    String guess = GuessGenerator.initialGuess();
    char[] responses = Handler.guessHandler(guess);

    // subsequent guess loop
    while (!checkCorrect(responses) && attempts < 5) {
      attempts++;
      System.out.println("Attempts remaining: " + (6 - attempts) + ".");
      GuessGenerator.removeWords(guess, responses);
      guess = GuessGenerator.nextGuess();
      responses = Handler.guessHandler(guess);
    }

    // check to see if correct or not
    if (checkCorrect(responses)) {
      System.out.println("\nGreat job program!");
      Handler.closeScanner();
    } else
      System.out.println("\nBad job program!");
    Handler.closeScanner();
  }

  /**
   * Determines if the computer guessed correctly if all responses were 'g'
   * 
   * @param responses - char[] of responses from the user's console
   * @return boolean - returns true if correct, false if incorrect
   */
  public static boolean checkCorrect(char[] responses) {
    for (char character : responses) {
      if (character != 'g') {
        return false;
      }
    }
    return true;
  }

}