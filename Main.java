package org.example;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Main {

    static Scanner scan = new Scanner(System.in);

    /*
    * User enters starting word and its colors
    * Make a new Guess instance with the word, colors, and available endWords (whole list at start)
    * Get the next guess using a guess class method
    * pull the available words from the guess class
    * Tell user next best word
    * Repeat until all green is entered
     */

    public static void main(String[] args) throws IOException {
        System.out.println("Recommended starting words ROATE or RAISE");
        Guess testGuess = new Guess("roate", new ColorSet("ybybb"), new ArrayList<>(Arrays.asList(readEndWords())));
        testGuess.eliminateAnswers();
        System.out.println(testGuess.getAnswerSet());
    }

    public static String[] readEndWords() throws IOException {
        Path path = Path.of("endWords.txt").toAbsolutePath();
        List<String> endWordLines = Files.readAllLines(path, Charset.defaultCharset());
        return endWordLines.toArray(new String[0]);
    }

    public static String[] readGuessWords() throws IOException {
        Path path = Path.of("guessWords.txt").toAbsolutePath();
        List<String> endWordLines = Files.readAllLines(path, Charset.defaultCharset());
        return endWordLines.toArray(new String[0]);
    }

    private static String getColorInput() {
        String str = "";
        boolean guessLoop = true;
        while (guessLoop) {
            str = scan.nextLine().toLowerCase();
            guessLoop = !isValidGuess(str);
        }
        System.out.println("Calculating...");
        return str;
    }

    private static boolean isValidGuess(String guess) {
        // I assume you'll be using isInWordle here, included for testing
        if (isValidGuessLength(guess) && isValidGuessCharacters(guess)) {
            return true;
        }
        System.out.println("Please try again");
        return false;
    }

    private static boolean isValidGuessCharacters(String guess) {
        for (int i = 0; i < guess.length(); i++) {
            if (!"bgy".contains(String.valueOf(guess.charAt(i)))) {
                System.out.println("Incorrect color format!");
                return false;
            }
        }
        return true;
    }

    private static boolean isValidGuessLength(String guess) {
        if (guess.length() == 5) {
            return true;
        }
        System.out.println("The guess must be 5 characters in length!");
        return false;
    }

    private static boolean continuePlaying() {
        System.out.println("Did that solve the game? [Y/N]");
        String prompt = scan.nextLine().toLowerCase();
        return prompt.equals("y");
    }
}