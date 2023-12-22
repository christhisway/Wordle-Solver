import java.util.Scanner;

/**
 * This object asks for the results of a guess and adjusts the remaining words
 * accordingly. In essence, it's a modified version of the Scanner.
 * 
 * @since 1.1
 */

public class Handler {

    private static Scanner sc = new Scanner(System.in);
    /**
     * Prompt/keyboard input handler for retrieving info from user.
     * 
     * @param guess - current guess
     * @return char[] - array of responses (either g/y/n) for each letter
     */
    public static char[] guessHandler(String guess) {
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
        return responses;
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
