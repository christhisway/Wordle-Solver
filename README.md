# Wordle Solver by Jayden Webb

## Description
Hello! This is a simple Wordle Solver, originally created as a project for an OOP class in my senior year of high school.
It works by suggesting a guess for the user to input into their Wordle game, 
using the feedback (green, yellow, or grey squares) to filter out any impossible words, 
then suggesting another guess until either the game ends or the correct answer is guessed.

The largest challenge of this project was implementing the word filtering; the seemingly simple constraints of green, yellow, and gray letters
in conjunction with the possibility of duplicate letters proved surprisingly difficult to filter down to explicit algorithms.

Created entirely in Java, the program makes use of file reading, user input, various String methods, and ArrayLists. 

## Installation and Usage
**Execute the following commands to download and run the Wordle Solver:**
   ```bash
   git clone https://github.com/christhisway/Wordle-Solver.git
   cd Wordle-Solver

   # Compile and run files
   javac Main.java
   java Main
   ```
  - Make sure you have Java installed on your system.

1. **Follow On-Screen Instructions:**
   - The program will prompt you with guesses, and you can provide feedback for each letter using:
   - 'g' (green: correct)
   - 'y' (yellow: in the wrong position)
   - 'n' (gray: not in the word)

2. **Repeat Until Solved:**
   - The solver will continue suggesting guesses based on your feedback until the correct word is identified.

3. **Enjoy Solving Wordles:**
   - Have fun using the Wordle Solver and mastering those challenging Wordle puzzles!

Feel free to explore the code and customize the solver as needed. If you encounter any issues or have suggestions, please open an issue on the GitHub repository.

Happy Wordle solving!

## To-Do List
In the future, I hope to:
- [x] Abstract the many functions into files of related functions.
- [ ] Rewrite the functions to be more concise and easier to read.
- [ ] Optimize the method of word removal.
- [ ] Implement a rudimentary AI that finds the most optimal guess for each turn, minimizing guesses.
- [ ] Add the ability for the user to define their own first guess.
- [ ] Add ability to regenerate a guess if the user does not like the guess that the app gives.
- [ ] \(Stretch Goal) Implement a functional GUI.
- [ ] \(Stretch Goal) Allow for undoing feedback entry, to fix accidental mistypes that may result in irreversable incorrect word removal.

A special thanks to my Procedural and Object-Oriented Programming instructor, Michael D'Argenio, who reintroduced me to Java.