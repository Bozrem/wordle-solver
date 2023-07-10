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
        boolean solved = false;
        System.out.println("Recommended starting words ROATE or RAISE");
        String guess = getGuess();
        ColorSet colors = getColors();
        Game game = new Game(guess, colors);
        if (colors.equals(new ColorSet("ggggg"))) solved = true;
        else {
            game.updateAnswerSet();
            System.out.println("Calculating...");
            printRemainingWords(game);
            System.out.println("\nNext best guess is " + game.getBestGuess().toUpperCase());
        }
        while (!solved && runNextGuess(game)) {

        }
        System.out.println("Congrats :)");

    }

    public static boolean runNextGuess(Game game) throws IOException {
        String guess = getGuess();
        ColorSet colors = getColors();
        if (colors.equals(new ColorSet("ggggg"))) return false;
        game.update(guess, colors);
        game.updateAnswerSet();
        System.out.println("Calculating...");
        if (game.getAvailableWords().size() == 1){
            System.out.println("The answer is " + game.getAvailableWords().get(0).toUpperCase());
            return false;
        }
        printRemainingWords(game);
        System.out.println("\nNext best guess is " + game.getBestGuess().toUpperCase());
        return true;
    }

    public static void printRemainingWords(Game game){
        ArrayList<String> list = game.getAvailableWords();
        System.out.println("The following answers are still possible: ");
        for (String answer : list){
            System.out.print(answer + " ");
        }
    }

    public static String getGuess(){
        System.out.println("guess: ");
        return scan.nextLine().toLowerCase();
    }

    public static ColorSet getColors(){
        System.out.println("colors: ");
        return new ColorSet(scan.nextLine().toLowerCase());
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
}