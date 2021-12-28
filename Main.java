package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static final String FILE_TYPE_TXT = ".txt";
    static final String FILE_TYPE_JSON = ".json";
    static final String REMOVE_SPECIAL_CHAR = "\\p{Punct}";
    static final String SINGLE_SPACE = "";
    static final String EXCLUDE_SPECIAL_CHAR = "[a-zA-Z0-9\"\"]";

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("File path and Search word cannot be empty");
            return;
        }
        else if(args.length > 2){
            System.out.println("There can only be two arguments");
            return;
        }
        String textFilePath = args[0];
        String searchKeyword = args[1];
        Matcher matcher = Pattern.compile(REMOVE_SPECIAL_CHAR).matcher(searchKeyword);
        while (matcher.find()) {
            System.out.println("Search word cannot contain any special characters");
            return;
        }
        File file = new File(textFilePath);
        System.out.println("Processing...");
        if (isValidFile(file)) {
            if (file.length() != 0) {
                wordCount(textFilePath, searchKeyword);
            } else {
                System.out.println("File does not contain any data");
            }
        }
    }

    private static boolean isValidFile(File file) {

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

        int wordCount = 0;
        String fileData;
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(textFilePath));
            while ((fileData = bufferedReader.readLine()) != null) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
