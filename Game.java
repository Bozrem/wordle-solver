package org.example;

import java.util.ArrayList;
import java.util.Arrays;

/*
To improve and clean up
- Reword function names
- Reword variable and loop variable names (not i or e)
- Divide run() function
 */
public class Game {
    private final Word guess;
    private final ArrayList<String> availableWords = new ArrayList<>();
    private final int originalLength;

    public Game(String guess, String answer, String[] answerDataset) {
        this.guess = new Word(guess);
        getColors(this.guess, answer);
        availableWords.addAll(Arrays.asList(answerDataset));
        originalLength = answerDataset.length;
    }

    public static void getColors(Word guess, String answer) {
        doGreenLetters(guess, answer);
        doYellowLetters(guess, answer);
    }

    public static void doGreenLetters(Word guess, String answer) {
        for (int i = 0; i < answer.length(); i++) {
            if (guess.getGuess().charAt(i) == answer.charAt(i)) {
                guess.setColor(i, Color.GREEN);
                guess.useLetter(i);
            }
        }
    }

    public static void doYellowLetters(Word guess, String answer) {
        for (int i = 0; i < answer.length(); i++) {
            for (int e = 0; e < answer.length(); e++) {
                if (!guess.isLetterUsed(e) && guess.getColor(i) != Color.GREEN && guess.getGuess().charAt(i) == answer.charAt(e)) {
                    guess.useLetter(e);
                    guess.setColor(i, Color.YELLOW);
                }
            }
        }
    }

    public boolean checkColors(Word guess, String testAnswer) {
        Word answerCheck = new Word(guess.getGuess());
        getColors(answerCheck, testAnswer);
        return guess.isEqualColors(answerCheck);
    }

    public void run() {
        for (int i = 0; i < availableWords.size(); i++) {
            if (!checkColors(guess, availableWords.get(i))) {
                availableWords.remove(i);
                i--;
            }
        }
    }

    public double getScore() {
        return 100 * (1 - (double) availableWords.size() / originalLength);
    }
}
