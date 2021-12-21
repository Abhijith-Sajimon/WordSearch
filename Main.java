package com.company;

import java.io.*;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args){

        if(args.length!=2)
        {
            System.out.println("File path and search word cannot be empty");
            System.exit(0);
        }
        String path=args[0];
        String search=args[1];
        File file=new File(path);
        System.out.println("Processing...");
        doesFileExist(file);
        wordCount(file,search);
    }

    private static void doesFileExist(File file)
    {
        if (!file.isFile())
        {
            System.out.println("File does not exist");
        } else isFileFormatSupported(file);
    }

    private static void isFileFormatSupported(File file)
    {
        if (!file.getName().endsWith("txt"))
        {
            System.out.println("File format not supported");
        }
    }

    private static int wordCount(File file,String search){

        String data = null;
        String removeSpecialChar="";
        try {
            FileReader reader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(reader);
            data = buffer.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(data!=null) {
                removeSpecialChar = data.replaceAll("\\p{Punct}", "");
        }else {
                System.out.println("Empty file");
                System.exit(1);
            }
        StringTokenizer st = new StringTokenizer(removeSpecialChar);
        int wordcount = 0;
        String temp = "";
        while (st.hasMoreTokens()) {
            temp = st.nextToken();
            if (search.equalsIgnoreCase(temp)) {
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
}
