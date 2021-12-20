package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        String path = args[0];
        String search = args[1];
        File file = new File(path);
        if (file.exists()) {
            readFile(file);
            System.out.println("Processing...");
            searchWord(path, search);
        } else {
            System.out.println("File not found");
        }
    }

    public static void readFile(File file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int searchWord(String path, String search) {
        StringTokenizer st = null;
        try {
            String data = stringClass(path);
            String dataNoPunct = data.replaceAll("\\p{Punct}", "");
            st = new StringTokenizer(dataNoPunct);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int wordcount = 0;
        int i = 0;
        for(String[] temp = new String[st.countTokens()]; st.hasMoreTokens(); ++i) {
            temp[i] = st.nextToken();
            if (search.equalsIgnoreCase(temp[i])) {
                ++wordcount;
            }
        }
        if (wordcount == 0) {
            System.out.println("Word does not exist");
        } else {
            System.out.println("Word found");
            System.out.println(search + " occurs " + wordcount + " times in the file");
        }
        return wordcount;
    }

    private static String stringClass(String filename) throws IOException {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(filename)));
        return data;
    }
}