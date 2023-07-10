package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {

    private String recentGuess;
    private ColorSet recentColors;
    private ArrayList<String> availableWords;


    public Game(String guess, ColorSet colors) throws IOException {
        recentGuess = guess;
        recentColors = colors;
        availableWords = new ArrayList<>(Arrays.asList(Main.readEndWords()));
    }

    public void updateAnswerSet(){
        Guess guess = new Guess(recentGuess, recentColors, availableWords);
        guess.eliminateAnswers();
    }

    public String getBestGuess() throws IOException {
        if (availableWords.size() == 1) return availableWords.get(0);
        String bestGuess = "nulll";
        double bestScore = 10000;
        for (String guess : Main.readGuessWords()){
            double sum = 0;
            for (int i = 0; i < availableWords.size(); i++){
                Guess testGame = new Guess(guess, availableWords.get(i), new ArrayList<>(availableWords));
                testGame.eliminateAnswers();
                sum += testGame.getAnswerSet().size();
            }
            if (sum < bestScore){
                bestScore = sum;
                bestGuess = guess;
            }
        }
        return bestGuess;
    }

    public void update(String guess, ColorSet colors){
        recentGuess = guess;
        recentColors = colors;
    }

    public ArrayList<String> getAvailableWords(){
        return availableWords;
    }


}
