package org.example;

import java.util.ArrayList;
import java.util.List;

public class Guess {
    private final String guess;
    private ColorSet colors = new ColorSet();
    private ArrayList<String> answerSet;

    private boolean[] usedLetters = new boolean[5];

    public Guess(String guess, String answer, ArrayList<String> answerSet){
        this.guess = guess;
        this.answerSet = answerSet;
        getColors(answer);
    }

    public Guess(String guess, ColorSet colors, ArrayList<String> answerSet){
        this.guess = guess;
        this.colors = colors;
        this.answerSet = answerSet;
    }

    public void getColors(String answer) {
        doGreenLetters(answer);
        doYellowLetters(answer);
    }

    public void doGreenLetters(String answer) {
        for (int i = 0; i < answer.length(); i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                colors.setColor(i, Color.GREEN);
                usedLetters[i] = true;
            }
        }
    }

    public void doYellowLetters(String answer) {
        for (int i = 0; i < answer.length(); i++) {
            for (int e = 0; e < answer.length(); e++) {
                if (!usedLetters[e] && colors.getColor(i) != Color.GREEN && guess.charAt(i) == answer.charAt(e)) {
                    usedLetters[e] = true;
                    colors.setColor(i, Color.YELLOW);
                }
            }
        }
    }

    public ColorSet getColors(){
        return colors;
    }

    public void eliminateAnswers(){
        for (int i = 0; i < answerSet.size(); i++){
            Guess answerElimination = new Guess(guess, answerSet.get(i), answerSet);
            if (!answerElimination.getColors().equals(colors)){
                answerSet.remove(i);
                i--;
            }
        }
    }

    public List<String> getAnswerSet(){
        return answerSet;
    }




}
