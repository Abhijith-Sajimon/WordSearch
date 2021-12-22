package com.company;

import java.io.*;
import java.util.StringTokenizer;

public class Main {

    final static String FILE_TYPE_TXT = ".txt";
    final static String FILE_TYPE_JSON = ".json";
    final static String REMOVE_SPECIAL_CHAR = "\\p{Punct}";
    final static String SINGLE_SPACE = "";

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("File path and search word cannot be empty");
            return;
        }
        String textFilePath = args[0];
        String searchKeyword = args[1];
        File file = new File(textFilePath);
        System.out.println("Processing...");
        if (validateFile(file)) {
            wordCount(file, searchKeyword);
        }
    }

    private static boolean validateFile(File file) {

        if (!file.exists()) {
            System.out.println("File does not exist");
            System.exit(0);
        } else if (!(file.getName().endsWith(FILE_TYPE_TXT) || file.getName().endsWith(FILE_TYPE_JSON))) {
            System.out.println("File format not supported");
            return false;
        }
        return true;
    }

    private static void wordCount(File file, String searchKeyword) {

        String fileData = "";
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            fileData = bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fileData != null) {
            fileData = fileData.replaceAll(REMOVE_SPECIAL_CHAR, SINGLE_SPACE);
        } else {
            System.out.println("File does not contain any data");
            return;
        }
        StringTokenizer str = new StringTokenizer(fileData);
        int wordcount = 0;
        while (str.hasMoreTokens()) {
            if (searchKeyword.equalsIgnoreCase(str.nextToken())) {
                wordcount++;
            }
        }
        if (wordcount == 0) {
            System.out.println("Word does not exist");
        } else {
            System.out.println("Word found");
            System.out.println(searchKeyword + " occurs " + wordcount + " times in the file");
        }
    }
}
