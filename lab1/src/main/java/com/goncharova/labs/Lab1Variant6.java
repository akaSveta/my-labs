package com.goncharova.labs;

import java.util.LinkedHashMap;

public class Lab1Variant6 {
    private static final String input_file = "C:\\Users\\sveta\\IdeaProjects\\my-labs\\lab1\\src\\main\\resources\\characters.csv";
    private static final String output_file = "result.txt";

    public static void main(String[] args) {
        WorkWithFile workWithFile = new WorkWithFile();
        LinkedHashMap<String, String> characters = workWithFile.readFromCsv(input_file);
        if (characters.isEmpty()) {
            System.out.println("Программа завершена: нет данных для обработки");
            return;
        } else {
            workWithFile.saveToFile(characters, output_file);
            CRUD crud = new CRUD(characters);
            crud.demonstrateCrud();
        }
    }
}