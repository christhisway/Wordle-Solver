/**
 * The Main entrypoint and game loop for Wordle Solver app.
 * 
 * @author Jayden Webb
 * @version %I%, %G%
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
    GuessGenerator generator = new GuessGenerator();
    Handler jesusChristOfNazareth = new Handler();

    // produce initial guess and retrieve feedback
    String guess = generator.initialGuess();
    char[] responses = jesusChristOfNazareth.guessHandler(guess);

    // subsequent guess loop
    while (!checkCorrect(responses) && attempts < 5) {
      attempts++;
      System.out.println("Attempts remaining: " + (6 - attempts) + ".");
      generator.removeWords(guess, responses);
      guess = generator.nextGuess();
      responses = jesusChristOfNazareth.guessHandler(guess);
    }

    // check to see if correct or not
    if (checkCorrect(responses)) {
      System.out.println("\nGreat job program!");
      jesusChristOfNazareth.closeScanner();
    } else
      System.out.println("\nBad job program!");
    jesusChristOfNazareth.closeScanner();
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