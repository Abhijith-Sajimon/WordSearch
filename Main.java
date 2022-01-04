package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.StringTokenizer;

public class Main {

    static final String FILE_TYPE_TXT = ".txt";
    static final String FILE_TYPE_JSON = ".json";
    static final String REMOVE_SPECIAL_CHAR = "[^a-zA-Z0-9 -]";
    static final String SINGLE_SPACE = "";
    static String searchResult;

    public static void main(String[] args) {

        String textFilePath;
        String searchKeyword;
        String errorMessage;
        if (args.length != 2) {
            errorMessage = "Provide valid filepath and search keyword";
            System.out.println(errorMessage);
            Database.databaseOperation(null, null, searchResult = "Failure", errorMessage, 0);
            return;
        }
        textFilePath = args[0];
        searchKeyword = args[1];
        File file = new File(textFilePath);
        System.out.println("Processing...");
        try {
            if (isValidFile(file, textFilePath, searchKeyword)) {
                if (file.length() != 0) {
                    wordCount(textFilePath, searchKeyword);
                } else {
                    errorMessage = "File does not contain any data";
                    System.out.println(errorMessage);
                    Database.databaseOperation(textFilePath, searchKeyword, searchResult = "Failure", errorMessage, 0);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidFile(File file, String textFilePath, String searchKeyword) throws ClassNotFoundException {
        String errorMessage;
        if (!file.exists()) {
            errorMessage = "File does not exist";
            System.out.println(errorMessage);
            Database.databaseOperation(textFilePath, searchKeyword, searchResult = "Failure", errorMessage, 0);
            return false;
        } else if (!(file.getName().endsWith(FILE_TYPE_TXT) || file.getName().endsWith(FILE_TYPE_JSON))) {
            errorMessage = "File format not supported";
            System.out.println(errorMessage);
            Database.databaseOperation(textFilePath, searchKeyword, searchResult = "Failure", errorMessage, 0);
            return false;
        }
        return true;
    }

    private static void wordCount(String textFilePath, String searchKeyword) {

        int count = 0;
        String errorMessage;
        String fileData;
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(textFilePath));
            while ((fileData = bufferedReader.readLine()) != null) {
                fileData = fileData.replaceAll(REMOVE_SPECIAL_CHAR, SINGLE_SPACE);
                StringTokenizer splitIntoToken = new StringTokenizer(fileData);
                while (splitIntoToken.hasMoreTokens()) {
                    if (searchKeyword.equalsIgnoreCase(splitIntoToken.nextToken())) {
                        count++;
                    }
                }
            }
            if (count == 0) {
                errorMessage = "Word does not exist";
                System.out.println(errorMessage);
                Database.databaseOperation(textFilePath, searchKeyword, searchResult = "Failure", errorMessage, count);
            } else {
                System.out.println("Word found");
                System.out.println(searchKeyword + " occurs " + count + " times in the file");
                Database.databaseOperation(textFilePath, searchKeyword, searchResult = "Success", null, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Database {

    private static String CONNECTION_URL = "jdbc:mysql://localhost:3306/elixr_labs_internship";
    private static String USER = "root";
    private static String PASSWORD = "abhijith.sajimon@29";

    public static void databaseOperation(String textFilePath, String searchKeyword, String searchResult, String errorMessage, int count) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String insertSql = "INSERT INTO audit VALUES(?,?,now(),?,?,?)";
        try {
            PreparedStatement statement;
            Connection connect = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            statement = connect.prepareStatement(insertSql);
            statement.setString(1, textFilePath);
            statement.setString(2, searchKeyword);
            statement.setString(3, searchResult);
            statement.setInt(4, count);
            statement.setString(5, errorMessage);
            statement.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
