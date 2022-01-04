package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.StringTokenizer;

import static com.company.Main.ERROR_MESSAGE;
import static com.company.Main.statement;

public class Main {

    static final String FILE_TYPE_TXT = ".txt";
    static final String FILE_TYPE_JSON = ".json";
    static final String REMOVE_SPECIAL_CHAR = "[^a-zA-Z0-9 -]";
    static final String SINGLE_SPACE = "";
    static String ERROR_MESSAGE;
    static PreparedStatement statement;
    static String textFilePath;
    static String searchKeyword;


    public static void main(String[] args) {

        if (args.length != 2) {
            ERROR_MESSAGE = "Provide valid filepath and search keyword";
            System.out.println(ERROR_MESSAGE);
            Database.wordSearchFailure(null, null, ERROR_MESSAGE);
            return;
        }
        textFilePath = args[0];
        searchKeyword = args[1];
        File file = new File(textFilePath);
        System.out.println("Processing...");
        try {
            if (isValidFile(file)) {
                if (file.length() != 0) {
                    wordCount(textFilePath, searchKeyword);
                } else {
                    ERROR_MESSAGE = "File does not contain any data";
                    System.out.println(ERROR_MESSAGE);
                    Database.wordSearchFailure(textFilePath, searchKeyword, ERROR_MESSAGE);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidFile(File file) throws ClassNotFoundException {

        if (!file.exists()) {
            ERROR_MESSAGE = "File does not exist";
            System.out.println(ERROR_MESSAGE);
            Database.wordSearchFailure(textFilePath, searchKeyword, ERROR_MESSAGE);
            return false;
        } else if (!(file.getName().endsWith(FILE_TYPE_TXT) || file.getName().endsWith(FILE_TYPE_JSON))) {
            ERROR_MESSAGE = "File format not supported";
            System.out.println(ERROR_MESSAGE);
            Database.wordSearchFailure(textFilePath, searchKeyword, ERROR_MESSAGE);
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
                fileData = fileData.replaceAll(REMOVE_SPECIAL_CHAR, SINGLE_SPACE);
                StringTokenizer splitIntoToken = new StringTokenizer(fileData);
                while (splitIntoToken.hasMoreTokens()) {
                    if (searchKeyword.equalsIgnoreCase(splitIntoToken.nextToken())) {
                        count++;
                    }
                }
            }
            if (count == 0) {
                ERROR_MESSAGE = "Word does not exist";
                System.out.println(ERROR_MESSAGE);
                Database.wordSearchFailure(textFilePath, searchKeyword, ERROR_MESSAGE);
            } else {
                System.out.println("Word found");
                System.out.println(searchKeyword + " occurs " + count + " times in the file");
                Database.wordSearchSuccess(textFilePath, searchKeyword, count);
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
    private static String SEARCH_RESULT_SUCCESS = "Success";
    private static String SEARCH_RESULT_FAILURE = "Failure";

    public static void wordSearchFailure(String textFilePath, String searchKeyword, String errorMessage) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String insertSql = "INSERT INTO audit VALUES(?,?,now(),?,?,?)";
        try {
            Connection connect = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            statement = connect.prepareStatement(insertSql);
            statement.setString(1, textFilePath);
            statement.setString(2, searchKeyword);
            statement.setString(3, SEARCH_RESULT_FAILURE);
            statement.setInt(4, 0);
            statement.setString(5, ERROR_MESSAGE);
            statement.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void wordSearchSuccess(String textFilePath, String searchKeyword, int count) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String insertSql = "INSERT INTO audit VALUES(?,?,now(),?,?,?)";
        try {
            Connection connect = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            statement = connect.prepareStatement(insertSql);
            statement.setString(1, textFilePath);
            statement.setString(2, searchKeyword);
            statement.setString(3, SEARCH_RESULT_SUCCESS);
            statement.setInt(4, count);
            statement.setString(5, null);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
