package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import com.company.dbconnection.DatabaseLink;

public class Main {

    static String textFilePath = null;
    static String searchKeyword = null;

    public static void main(String[] args) {

        if((args.length != 2) || (args[0] == null || args[0] == " " || args[0] == "") || (args[1] == null || args[1] == " " || args[1] == "")) {
            Constants.errorMessage1 = "Both file path and Search word must contain some value";
            System.out.println(Constants.errorMessage1);
            DatabaseLink databaseLink = new DatabaseLink(textFilePath, searchKeyword, Constants.FAILURE ,Constants.errorMessage1, 0);
            databaseLink.start();
            return;
        }
            textFilePath = args[0];
            searchKeyword = args[1];
            File file = new File(textFilePath);
            System.out.println("Processing...");
            if(isValidFile(file, textFilePath, searchKeyword)) {
                if (file.length() != 0) {
                    wordCount(textFilePath, searchKeyword);
                } else {
                    Constants.errorMessage2 = "File does not contain any data";
                    System.out.println(Constants.errorMessage2);
                    DatabaseLink databaseLink = new DatabaseLink(textFilePath, searchKeyword, Constants.FAILURE,Constants.errorMessage2, 0);
                    databaseLink.start();
                }
            }
        }

        private static boolean isValidFile(File file, String textFilePath, String searchKeyword) {
        if (!file.exists()) {
            Constants.errorMessage3 = "File does not exist";
            System.out.println(Constants.errorMessage3);
            DatabaseLink databaseLink = new DatabaseLink(textFilePath, searchKeyword, Constants.FAILURE, Constants.errorMessage3, 0);
            databaseLink.start();
            return false;
        } else if (!(file.getName().endsWith(Constants.FILE_TYPE_TXT) || file.getName().endsWith(Constants.FILE_TYPE_JSON))) {
            Constants.errorMessage4 = "File format not supported";
            System.out.println(Constants.errorMessage4);
            DatabaseLink databaseLink = new DatabaseLink(textFilePath, searchKeyword, Constants.FAILURE, Constants.errorMessage4, 0);
            databaseLink.start();
            return false;
        }
        return true;
    }

    private static void wordCount(String textFilePath, String searchKeyword) {

        int count = 0;
        String fileData;
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(textFilePath));
            while ((fileData = bufferedReader.readLine()) != null) {
                fileData = fileData.replaceAll(Constants.REMOVE_SPECIAL_CHAR, Constants.SINGLE_SPACE);
                StringTokenizer splitIntoToken = new StringTokenizer(fileData);
                while (splitIntoToken.hasMoreTokens()) {
                    if (searchKeyword.equalsIgnoreCase(splitIntoToken.nextToken())) {
                        count++;
                    }
                }
            }
            if (count == 0) {
                Constants.errorMessage5 = "Word not found";
                System.out.println(Constants.errorMessage5);
                DatabaseLink databaseLink = new DatabaseLink(textFilePath, searchKeyword, Constants.FAILURE, Constants.errorMessage5, count);
                databaseLink.start();
            } else {
                System.out.println("Word found");
                System.out.println(searchKeyword + " occurs " + count + " times in the file");
                DatabaseLink databaseLink = new DatabaseLink(textFilePath, searchKeyword, Constants.SUCCESS, Constants.errorMessage, count);
                databaseLink.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
