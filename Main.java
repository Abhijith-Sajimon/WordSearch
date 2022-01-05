package com.company;

import java.io.*;

import com.company.dbconnection.DatabaseLink;

public class Main {

    static DatabaseLink databaseLink = new DatabaseLink();

    public static void main(String[] args) {

        String textFilePath = null;
        String searchKeyword = null;
        if ((args[0] != null && !args[0].isBlank()) || (args[1] != null && !args[1].isBlank())) {
            System.out.println(Constants.ERROR_MESSAGE_INVALID_INPUT);
            databaseLink.DatabaseConnection(textFilePath, searchKeyword, Constants.FAILURE, Constants.ERROR_MESSAGE_INVALID_INPUT, 0);
            return;
        }
        textFilePath = args[0];
        searchKeyword = args[1];
        File file = new File(textFilePath);
        System.out.println("Processing...");
        if (isValidFile(file, textFilePath, searchKeyword)) {
            if (file.length() != 0) {
                SearchWord searchWord = new SearchWord(textFilePath,searchKeyword);
                Thread thread = new Thread(searchWord);
                thread.start();
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
