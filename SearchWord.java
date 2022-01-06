package com.company;

import com.company.dbconnection.DatabaseLink;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public class SearchWord extends Thread {

    private final DatabaseLink databaseLink;
    private final String textFilePath;
    private final String searchKeyword;

    public SearchWord(String textFilePath, String searchKeyword, DatabaseLink databaseLink) {

        this.textFilePath = textFilePath;
        this.searchKeyword = searchKeyword;
        this.databaseLink = databaseLink;
    }

    public void run() {
        int count = 0;
        String fileData;
        String errorMessage = null;
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
                System.out.println(Constants.ERROR_MESSAGE_WORD_NOT_FOUND);
                databaseLink.DatabaseConnection(textFilePath, searchKeyword, Constants.FAILURE, Constants.ERROR_MESSAGE_WORD_NOT_FOUND, count);
            } else {
                System.out.println("Word found");
                System.out.println(searchKeyword + " occurs " + count + " times in the file");
                databaseLink.DatabaseConnection(textFilePath, searchKeyword, Constants.SUCCESS, errorMessage, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
