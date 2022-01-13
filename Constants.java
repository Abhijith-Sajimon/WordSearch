package com.company;

public class Constants {

    public static final String FILE_TYPE_TXT = ".txt";
    public static final String FILE_TYPE_JSON = ".json";
    public static final String REMOVE_SPECIAL_CHAR = "[^a-zA-Z0-9 -]";
    public static final String SINGLE_SPACE = " ";
    public static final String ERROR_MESSAGE_INCORRECT_NUMBER_OF_INPUTS = "The number of inputs must not exceed or subceed two. Please input file path in double quotes if it contains any white spaces.";
    public static final String ERROR_MESSAGE_FILE_PATH_EMPTY_OR_NULL = "File path cannot be empty.";
    public static final String ERROR_MESSAGE_SEARCH_WORD_EMPTY_OR_NULL = "Search word cannot be empty.";
    public static final String ERROR_MESSAGE_EMPTY_FILE = "File does not contain any data.";
    public static final String ERROR_MESSAGE_FILE_NOT_FOUND = "File does not exist.";
    public static final String ERROR_MESSAGE_FILE_FORMAT_NOT_SUPPORTED = "File format not supported.";
    public static final String ERROR_MESSAGE_WORD_NOT_FOUND = "Word not found.";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    public static final String INSERT_SQL_STATEMENT = "INSERT INTO audit VALUES(?,?,now(),?,?,?)";
    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/elixr_labs_internship";
    public static final String USER = "root";
    public static final String PASSWORD = "Vivek@12";
}
