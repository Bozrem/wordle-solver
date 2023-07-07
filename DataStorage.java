package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DataStorage {
    private Map<String, String> dataMap;

    public DataStorage() {
        dataMap = new HashMap<>();
    }

    public void updateWord(ColorSet colors, String bestGuess, String filePath) {
        loadFromFile(filePath);
        dataMap.put(colors.toStringForm(), bestGuess);
        saveToFile(filePath);
    }

    public String getStringData(ColorSet colors) {
        return dataMap.get(colors.toStringForm());
    }

    public void saveToFile(String filePath) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(dataMap);
            //System.out.println("Data map has been saved to: " + filePath);

        } catch (IOException e) {
            System.out.println("Error occurred while saving data map to file: " + e.getMessage());
        }
    }

    public void loadFromFile(String filePath) {
        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            dataMap = (Map<String, String>) objectIn.readObject();
            //System.out.println("Data map has been loaded from: " + filePath);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error occurred while loading data map from file: " + e.getMessage());
        }
    }

    public void clearData() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Are you sure you want to clear the data? [Yes/No]");
        String prompt = scan.nextLine().toLowerCase();
        if (prompt.equals("yes")) {
            dataMap = new HashMap<>();
        }
    }
}
