package com.company.dbconnection;

import com.company.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseLink {

    public void DatabaseConnection(String textFilePath, String searchKeyword, String searchResult, String errorMessage, int count) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connect = null;
        try {
            PreparedStatement statement;
            connect = DriverManager.getConnection(Constants.CONNECTION_URL, Constants.USER, Constants.PASSWORD);
            statement = connect.prepareStatement(Constants.insertSql);
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
                if (connect != null) {
                    connect.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
