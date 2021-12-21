package com.company;

import java.io.*;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("File path and search word cannot be empty");
            return;
        }
        String textFilePath = args[0];
        String searchKeyword = args[1];
        File file = new File(textFilePath);
        System.out.println("Processing...");
        validateFile(file);
        if (validateFile(file)) {
            wordCount(file, searchKeyword);
        }
    }

    private static boolean validateFile(File file) {

        if (!file.exists()) {
            System.out.println("File does not exist");

        } else if (!(file.getName().endsWith(".txt") || file.getName().endsWith(".json"))) {
            System.out.println("File format not supported");
            System.exit(0);
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
            fileData = fileData.replaceAll("\\p{Punct}", "");
        } else {
            System.out.println("File does not contain any data");
            return;
        }
        StringTokenizer st = new StringTokenizer(fileData);
        int wordcount = 0;
        while (st.hasMoreTokens()) {
            if (searchKeyword.equalsIgnoreCase(st.nextToken())) {
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
