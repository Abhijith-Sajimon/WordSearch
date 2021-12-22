package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public class Main {

    final static String FILE_TYPE_TXT = ".txt";
    final static String FILE_TYPE_JSON = ".json";
    final static String REMOVE_SPECIAL_CHAR = "\\p{Punct}";
    final static String SINGLE_SPACE = "";

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("File path and Search word cannot be empty");
            return;
        }
        String textFilePath = args[0];
        String searchKeyword = args[1];
        File file = new File(textFilePath);
        System.out.println("Processing...");
        if (validateFile(file)) {
            wordCount(textFilePath, searchKeyword);
        }
    }

    private static boolean validateFile(File file) {

        if (!file.exists()) {
            System.out.println("File does not exist");
            return false;
        } else if (!(file.getName().endsWith(FILE_TYPE_TXT) || file.getName().endsWith(FILE_TYPE_JSON))) {
            System.out.println("File format not supported");
            return false;
        }
        return true;
    }

    private static void wordCount(String textFilePath, String searchKeyword) {

        StringBuilder stringBuilder = new StringBuilder();
        int wordCount = 0;
        String fileData;
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(textFilePath));
            fileData = bufferedReader.readLine();
            if (fileData == null) {
                System.out.println("File does not contain any data");
                return;
            } else {
                while (fileData != null) {
                    stringBuilder.append(fileData).append("\n");
                    fileData = fileData.replaceAll(REMOVE_SPECIAL_CHAR, SINGLE_SPACE);
                    StringTokenizer splitIntoToken = new StringTokenizer(fileData);
                    while (splitIntoToken.hasMoreTokens()) {
                        if (searchKeyword.equalsIgnoreCase(splitIntoToken.nextToken())) {
                            wordCount++;
                        }
                    }
                }
                if (wordCount == 0) {
                    System.out.println("Word does not exist");
                } else {
                    System.out.println("Word found");
                    System.out.println(searchKeyword + " occurs " + wordCount + " times in the file");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

