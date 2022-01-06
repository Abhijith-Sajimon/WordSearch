package com.company;

import java.io.*;

import com.company.dbconnection.DatabaseLink;

public class Main {

    static DatabaseLink databaseLink = new DatabaseLink();

    public static void main(String[] args) {

        String textFilePath = null;
        String searchKeyword = null;
        if (args.length != 2) {
            System.out.println(Constants.ERROR_MESSAGE_INCORRECT_NUMBER_OF_INPUTS);
            databaseLink.DatabaseConnection(textFilePath, searchKeyword, Constants.FAILURE, Constants.ERROR_MESSAGE_INCORRECT_NUMBER_OF_INPUTS, 0);
            return;
        }
        textFilePath = args[0];
        searchKeyword = args[1];
        if (textFilePath == null || textFilePath.isEmpty()) {
            System.out.println(Constants.ERROR_MESSAGE_FILE_PATH_EMPTY_OR_NULL);
            databaseLink.DatabaseConnection(textFilePath, searchKeyword, Constants.FAILURE, Constants.ERROR_MESSAGE_FILE_PATH_EMPTY_OR_NULL, 0);
            return;
        }
        if (searchKeyword == null || searchKeyword.isEmpty()) {
            System.out.println(Constants.ERROR_MESSAGE_SEARCH_WORD_EMPTY_OR_NULL);
            databaseLink.DatabaseConnection(textFilePath, searchKeyword, Constants.FAILURE, Constants.ERROR_MESSAGE_SEARCH_WORD_EMPTY_OR_NULL, 0);
            return;
        }
        File file = new File(textFilePath);
        System.out.println("Processing...");
        if (isValidFile(file, textFilePath, searchKeyword)) {
            if (file.length() != 0) {
                SearchWord searchWord = new SearchWord(textFilePath, searchKeyword, databaseLink);
                searchWord.start();
                System.out.println(searchWord.isAlive());
                System.out.println(searchWord.getName());
                try {
                    searchWord.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(Constants.ERROR_MESSAGE_EMPTY_FILE);
                databaseLink.DatabaseConnection(textFilePath, searchKeyword, Constants.FAILURE, Constants.ERROR_MESSAGE_EMPTY_FILE, 0);
            }
        }
    }

    private static boolean isValidFile(File file, String textFilePath, String searchKeyword) {
        if (!file.exists()) {
            System.out.println(Constants.ERROR_MESSAGE_FILE_NOT_FOUND);
            databaseLink.DatabaseConnection(textFilePath, searchKeyword, Constants.FAILURE, Constants.ERROR_MESSAGE_FILE_NOT_FOUND, 0);
            return false;
        } else if (!(file.getName().endsWith(Constants.FILE_TYPE_TXT) || file.getName().endsWith(Constants.FILE_TYPE_JSON))) {
            System.out.println(Constants.ERROR_MESSAGE_FILE_FORMAT_NOT_SUPPORTED);
            databaseLink.DatabaseConnection(textFilePath, searchKeyword, Constants.FAILURE, Constants.ERROR_MESSAGE_FILE_FORMAT_NOT_SUPPORTED, 0);
            return false;
        }
        return true;
    }
}
