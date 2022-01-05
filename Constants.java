package com.company;

public class Constants {

        static final String FILE_TYPE_TXT = ".txt";
        static final String FILE_TYPE_JSON = ".json";
        static final String REMOVE_SPECIAL_CHAR = "[^a-zA-Z0-9 -]";
        static final String SINGLE_SPACE = "";
        static final String ERROR_MESSAGE_INVALID_INPUT = "Both file path and Search word must contain some value";
        static final String ERROR_MESSAGE_EMPTY_FILE = "File does not contain any data";
        static final String ERROR_MESSAGE_FILE_NOT_FOUND = "File does not exist";
        static final String ERROR_MESSAGE_FILE_FORMAT_NOT_SUPPORTED = "File format not supported";
        static final String ERROR_MESSAGE_WORD_NOT_FOUND = "Word not found";
        static final String SUCCESS = "SUCCESS";
        static final String FAILURE = "FAILURE";
        public static final String insertSql = "INSERT INTO audit VALUES(?,?,now(),?,?,?)";
        public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/elixr_labs_internship";
        public static final String USER = "root";
        public static final String PASSWORD = "abhijith.sajimon@29";
    }


