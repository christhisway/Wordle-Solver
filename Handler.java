import java.util.Scanner;

/**
 * This object asks for the results of a guess and adjusts the remaining words
 * accordingly. In essence, it's a modified version of the Scanner.
 * 
 * @since 0.2
 */

public class Handler {

    private static Scanner sc = new Scanner(System.in);

    /**
     * Prompt/keyboard input handler for retrieving info from user.
     * 
     * @param guess - current guess
     * @return char[] - array of responses (either g/y/n) for each letter
     */
    public static char[] getFeedback(String guess) {
        System.out.println("\nGuess: " + guess);
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
        return responses;
    }

    public static String nextGuess(int attempts){
        System.out.print("Would you like to give your own guess (y) or generate a new one (n): ");
        boolean userDefined = (sc.nextLine().toLowerCase().charAt(0) == 'y') ? true : false;
        String guess = new String();
        if (userDefined){
            guess = userDefinedGuess();
        } else {
            guess = Brain.nextGuess(attempts);
        }
        return guess;
    }

    private static String userDefinedGuess(){
        String userGuess = "";
        while (userGuess.equals("")){
            System.out.print("Enter your five-letter guess here: ");
            userGuess = sc.nextLine();
            try {
                userGuess = userGuess.substring(0, 5);
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Not enough letters!");
                userGuess = "";
                continue;
            }
            if (!allAlphabetical(userGuess)) {
                System.out.println("Invalid guess: only use alphabetical characters.");
                userGuess = "";
                continue;
            }
            if (!Brain.validGuesses.contains(userGuess)){
                System.out.println("That guess is not in the word list!");
                userGuess = "";
                continue;
            }
        }
        /*
        * Assert the following:
        * of length 5
        * all alphabetical
        * is a valid guess (cross-check with word list)
        */
        return userGuess;
    }

    private static boolean allAlphabetical(String str){
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Closes the Scanner when it's no longer needed.
     */
    public static void closeScanner() {
        if (sc != null) {
            sc.close();
        }
    }

}
