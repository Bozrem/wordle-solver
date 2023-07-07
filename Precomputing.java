package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Precomputing {
    /*
     * At the start of the program pull the current map
     * Have an update function that assigns a word to the color
     * Program can then come back to where if left off by going through colors until one is unassigned
     * */
    public static void main(String[] args) throws IOException {
        String[] guessWords = Main.readGuessWordsArray();
        String[] endWords = Main.readEndWords();
        DataStorage storage = new DataStorage();
        storage.loadFromFile("colorBase.dat");

        /*
        * Have a getNextColorSet function that increments through colors starting at all gray until one is "nulll"
        * getNextColorSet can also return a String that marks that there aren't any more colors to compute (in while loop)
        *  Create a game instance with the nextColorSet
        *  Run it and get the words remaining
        *  Create a for loop to go through all the guesses
        *   Create for loop to go through the available words
        *    Build new game instance with guess and availableWords[i] and availableWords
        *    Run new game instance
        *    Add score to sum
        *   Get average score, if it's the best for that ColorSet save it
        *  Load bestGuess and the ColorSet to storage
         */

        ColorSet colorset = new ColorSet();
        while (colorset.incrementColor()){
            ArrayList<String> availableWords = getAvailableWords(colorset, endWords);
            //System.out.println(availableWords + "\n"); for debug
            double bestScore = 0;
            String bestGuess = "";
            for (String guess : guessWords){
                double averageScore = getAverageScore(guess, availableWords);
                if (averageScore > bestScore){
                    bestGuess = guess;
                    bestScore = averageScore;
                    System.out.println(bestGuess + " " + bestScore);
                }
            }
            System.out.println(colorset + " " + bestGuess);
            //storage.updateWord(colorset, bestGuess, "colorBase.dat");
        }
    }

    public static double getAverageScore(String guess, ArrayList<String> availableWords){
        double sum = 0;
        for (String answer : availableWords){
            Game newGame = new Game(guess, answer, availableWords.toArray(new String[0]));
            newGame.run();
            sum += newGame.getScore();
        }
        return sum / (double) availableWords.size();
    }

    public static ArrayList<String> getAvailableWords(ColorSet colors, String[] endWords){
        Game game = new Game("roate", colors, endWords);
        game.run();
        return game.getAvailableWords();
    }

    public static void initializeFile(DataStorage storage){
        Color[] colors = new Color[5];
        for (int i = 0; i < 5; i++){
            colors[i] = Color.GRAY;
        }
        ColorSet colorSet = new ColorSet(colors);
        while (colorSet.incrementColor()){
            storage.updateWord(colorSet,"nulll", "colorBase.dat");
        }
        storage.saveToFile("colorBase.dat");
    }
}


