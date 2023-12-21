1. What are your criteria for programmatically determining your first pick?
   A word with 5 different letters, likely two vowels. This will probably go the furthest in the way of eliminating as many wrong letters as possible. Of the words that meet these criteria, one will be chosen at random.
2. How will you represent the data about the results of the guesses (what letters were green/yellow/grey, where they were located, etc.)? What data types will you use? Make sure you consider all of the following cases.
   -I will not have to store the incorrect letters. Each word containing an incorrect letter will be removed immediately following each guess.
   -Correctly positioned letters will be stored in an array of single-character STRINGS with a size of 5. The default value will be null for all five, and as characters are positioned correctly, they will be placed at the corresponding index.
   -Following each guess, I will remove words that have yellow letters at the guessed location. In addition, I will store the yellow letters in an ArrayList.
   -During the removal methods, I'll check if there are duplicate letters. This set of rules must take precedence over the others. If so, an entire extra set of rules will be applied:
   >If both instances are gray, remove words as normal.
   >If NOT both instances are gray: (! gray && gray)
   >>>Remove words where the position of [letter] matches that where [letter] was revealed to be gray OR yellow.
   >>>If an instance is green, store it in the [correctPositioned] array.
4. What are your criteria for all subsequent picks?
   - Following the removal methods (contains incorrect letters, does not align with correctly positioned letters, contains yellow letters at incorrect locations, does not contain all yellow letters), I'm not sure. Perhaps I'll sort by the frequencies given in words_freqs.csv. Or maybe it will be chosen at random.