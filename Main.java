package com.company;

import java.io.*;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("File path and search word cannot be empty");
            return;
        }
        String path = args[0];
        String search = args[1];
        File file = new File(path);
        System.out.println("Processing...");
        validateFile(file);
        if (!validateFile(file)) {
            wordCount(file, search);
        }
    }

    private static boolean validateFile(File file) {

        if (!file.exists()) {
            System.out.println("File does not exist");
            System.exit(0);
        } else {
            String fullName = file.getName();
            if (!(fullName.endsWith(".txt") || fullName.endsWith(".json"))) {
                System.out.println("File format not supported");
                System.exit(0);
            }
        }
        return false;
    }

    private static void wordCount(File file, String search) {

        String data = "";
        try {
            FileReader reader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(reader);
            data = buffer.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data != null) {
            data = data.replaceAll("\\p{Punct}", "");
        } else {
            System.out.println("Empty file");
            return;
        }
        StringTokenizer st = new StringTokenizer(data);
        int wordcount = 0;
        while (st.hasMoreTokens()) {
            if (search.equalsIgnoreCase(st.nextToken())) {
                wordcount++;
            }
        }
        if (wordcount == 0) {
            System.out.println("Word does not exist");
        } else {
            System.out.println("Word found");
            System.out.println(search + " occurs " + wordcount + " times in the file");
        }
    }
}
