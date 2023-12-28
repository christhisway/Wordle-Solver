import java.io.*;
import java.nio.file.*;
import java.util.*;
 
/**
 * A quick and dirty program that was used to gather the frequencies of the words in the approved solutions list.
 * 
 * @since 0.3
 */

public class FrequencyGrabber {
    
    public static void main(String[] args) {
        ArrayList<Double> frequencies = getFrequencies();
        ArrayList<String> solutions = getSolutions();
        ArrayList<String> words = getWords();
        String solFreqString = "";
        Collections.sort(solutions);
        for (String solution : solutions){
            if (words.indexOf(solution) == -1){
                System.out.println("Failed: " + solution);
                continue;
            }
            solFreqString += (solution.substring(0, 5) + "," + frequencies.get(words.indexOf(solution)) + "\n");
        }
        try{
            FileWriter solutionsWriter = new FileWriter("solutionFrequencies.csv");
            solutionsWriter.write(solFreqString);
            solutionsWriter.close();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
        
        // System.out.println(frequencies);
        //sort solutions
        //grab all frequencies (in order)
        //output word,freq for each word
    }

    private static ArrayList<Double> getFrequencies() {
        Path freqPath = Path.of("solutions.csv");
        String freqString = null;
        try {
            freqString = Files.readString(freqPath);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
        String[] freqArray = freqString.split("\n");
        ArrayList<Double> freqArrayList = new ArrayList<>();
        for (String freqLine : freqArray) {
            Double frequency = Double.parseDouble(freqLine.substring(6));
            freqArrayList.add(frequency);
        }
        return freqArrayList;
    }

    private static ArrayList<String> getSolutions() {
        Path solutionPath = Path.of("solution_list.csv");
        String solutionString = null;
        try {
            solutionString = Files.readString(solutionPath);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
        String[] solutionArray = solutionString.split("\n");
        ArrayList<String> solutionArrayList = new ArrayList<>();
        for (String solution : solutionArray) {
            solutionArrayList.add(solution);
        }
        return solutionArrayList;
    }

    private static ArrayList<String> getWords() {
        // only returns words with non-zero frequencies
        Path wordsPath = Path.of("words.txt");
        String wordsString = null;
        try {
            wordsString = Files.readString(wordsPath);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
        String[] wordsArray = wordsString.split("\n");
        return new ArrayList<>(Arrays.asList(wordsArray));
    }

}
