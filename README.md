# Wordle Solver by Jayden Webb

## Description
This is a simple Wordle Solver, originally created as a project for an OOP class in my senior year of high school.
It works by suggesting a guess for the user to input into their Wordle game, 
using the feedback (green, yellow, or grey squares) to filter out any impossible words, 
then suggesting another guess until either the game ends or the correct answer is guessed.

It is optimized such that, in my testing, it has yet to fail to guess the answer in the allocated six attempts.

The largest challenge of this project was implementing the word filtering; the seemingly simple constraints of green, yellow, and gray letters
in conjunction with the possibility of duplicate letters proved surprisingly difficult to filter down to explicit algorithms
when all cases must be considered separately and put together in a way that works properly.

Created entirely in Java, the program makes use of file reading, user input, various String methods, and ArrayLists, and a few simple algorithms to optimize the solving of the Wordle.

## Installation and Usage
**Execute the following commands to download and run the Wordle Solver:**
   ```bash
   git clone https://github.com/christhisway/Wordle-Solver.git
   cd Wordle-Solver

   javac Main.java
   java Main
   ```
   Make sure you have Java installed on your system.

1. **Follow On-Screen Instructions:**
   - The program will prompt you with words to enter into the Wordle, and you can provide feedback for each letter using:
      - **'g'** (green: correct)
      - **'y'** (yellow: in the wrong position)
      - **'n'** (gray: not in the word)

2. **Repeat Until Solved:**
   - The solver will continue suggesting guesses based on your feedback until the correct word is identified.

3. **Enjoy Solving Wordles!**

Feel free to explore the code and customize the solver as needed. If you encounter any issues or have suggestions, please open an issue on the GitHub repository.

Happy Wordle solving!

## To-Do List
In the future, I hope to:
- [x] Abstract the many functions into files of related functions. (v0.2)
- [x] Rewrite the functions to be more concise and easier to read. (v0.2)
- [x] Improve frequency filtering of the list such that the only guesses provided are ones that could reasonably be the answer. (This is important because the solutions to the NYT Wordle are always chosen by a human.) (v0.3)
- [x] Optimize the method of word removal to take less time and memory. (v0.2)
- [X] Implement a rudimentary AI that finds the most optimal guess for each turn, minimizing guesses. (v0.3)
- [x] Add the ability for the user to define their own guesses. (v0.3)
- [ ] Add ability to regenerate a guess if the user does not like the guess that the app gives.
- [ ] Add a catch that does not allow the user to accidentally input feedback that conflicts with previously given feedback. (e.g. putting 'n' for a letter that was previously indicated as 'g' in the same position)
- [ ] \(Stretch Goal) Implement a functional GUI.
- [ ] \(Stretch Goal) Allow for undoing feedback entry, to fix accidental mistypes that may result in irreversable incorrect word removal.
- [ ] \(Stretch Goal) Use a script to test the solver's accuracy against hundreds of Wordle games to prove 100% efficacy.
- [ ] \(Stretch Goal) Use a list of Wordle's past answers to move those words to the bottom of the list, since the editors try not to repeat answers if they can help it.

A special thanks to my Procedural and Object-Oriented Programming instructor, Michael D'Argenio, who reintroduced me to Java.
