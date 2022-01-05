package com.company.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseLink extends Thread {

    private static final String insertSql = "INSERT INTO audit VALUES(?,?,now(),?,?,?)";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/elixr_labs_internship";
    private static final String USER = "root";
    private static final String PASSWORD = "abhijith.sajimon@29";
    private String textFilePath;
    private String searchKeyword;
    private int count;
    private String searchResult;
    private String errorMessage;

    public DatabaseLink(String textFilePath, String searchKeyword, String searchResult, String errorMessage, int count) {

        this.textFilePath = textFilePath;
        this.searchKeyword = searchKeyword;
        this.count = count;
        this.searchResult = searchResult;
        this.errorMessage = errorMessage;
    }

        public void run(){

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection connect = null;
            try {
                PreparedStatement statement;
                connect = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
                statement = connect.prepareStatement(insertSql);
                statement.setString(1, textFilePath);
                statement.setString(2, searchKeyword);
                statement.setString(3, searchResult);
                statement.setInt(4, count);
                statement.setString(5, errorMessage);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connect.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


