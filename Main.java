package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;


public class Main {

    static Scanner scan = new Scanner(System.in);
    static HashSet<String> validGuessWords;

    public static void main(String[] args) throws IOException {
        validGuessWords = readGuessWords();
        printIntro();
        boolean gameLoop = true;
        while (gameLoop) {
            System.out.println("\nEnter the word to score. Must contain 5 alphabetical letters");
            String guess = getGuess();
            System.out.println("\n\n\n\n\n\n\n\n\nCalculating score of " + guess.toUpperCase() + ". Please wait...");
            System.out.println(guess.toUpperCase() + " received an average of " + getScore(guess) + "% answers removed!");
            gameLoop = continuePlaying();
        }
        printOutro();
    }

    public static HashSet<String> readGuessWords() {
        HashSet<String> hashSet = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("guessWords.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                hashSet.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hashSet;
    }

    public static String[] readEndWords() throws IOException {
        Path path = Path.of("endWords.txt").toAbsolutePath();
        List<String> endWordLines = Files.readAllLines(path, Charset.defaultCharset());
        return endWordLines.toArray(new String[0]);
    }

    private static void printIntro() {
        System.out.println("*****************************************\n* Welcome to the Wordle Stat simulator! *");
        System.out.println("\nThis program works by running your guess with every answer that Wordle has to offer.");
        System.out.println("It then takes the colors you would have generated from the guess to rule out the answers that don't work");
        System.out.println("and creates a score for what percent of the answers it eliminated in that game, and returns the average for all games!");
    }

    private static String getGuess() {
        String str = "";
        boolean guessLoop = true;
        while (guessLoop) {
            str = scan.nextLine().toLowerCase();
            guessLoop = !isValidGuess(str);
        }
        return str;
    }

    private static boolean isValidGuess(String guess) {
        // I assume you'll be using isInWordle here, included for testing
        if (isValidGuessLength(guess) && isValidGuessCharacters(guess) && isInWordle(guess)) {
            return true;
        }
        System.out.println("Please try again");
        return false;
    }

    private static boolean isValidGuessCharacters(String guess) {
        for (int i = 0; i < guess.length(); i++) {
            if (!"abcdefghijklmnopqrstuvwxyz".contains(String.valueOf(guess.charAt(i)))) {
                System.out.println("Non-alphabetical characters!");
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

    private static boolean isInWordle(String guess) {
        if (validGuessWords.contains(guess)) {
            return true;
        }
        System.out.println(guess + " is not an allowed guess in Wordle!");
        return false;
    }

    private static void buildGames(String guess, Game[] games) throws IOException {
        String[] endWords = readEndWords();
        for (int i = 0; i < games.length; i++) {
            games[i] = new Game(guess, endWords[i], endWords);
        }
    }

    public static void runGames(Game[] games) {
        for (Game game : games) {
            game.run();
        }
    }

    private static double getScore(String guess) throws IOException {
        String[] endWords = readEndWords();
        final Game[] games = new Game[endWords.length];
        buildGames(guess, games);
        runGames(games);
        return Math.round(getPercentRemoved(games) * 100.0) / 100.0;
    }

    public static double getPercentRemoved(Game[] games) {
        double sum = 0;
        for (Game game : games) {
            sum += game.getScore();
        }
        return sum / games.length;
    }

    private static boolean continuePlaying() {
        System.out.println("Would you like to try again? [Y/N]");
        String prompt = scan.nextLine().toLowerCase();
        return prompt.equals("y");
    }

    private static void printOutro() {
        System.out.println("*****************************************");
        System.out.println("Thank you for playing!\nSoftware created by Bozrem");
        System.out.println("*****************************************");
    }


}