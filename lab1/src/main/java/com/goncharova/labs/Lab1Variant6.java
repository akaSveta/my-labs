package com.goncharova.labs;

import java.util.LinkedHashMap;

public class Lab1Variant6 {
    private static final String OUTPUT_FILE = "result.txt";

    public static void main(String[] args) {
        WorkWithFile workWithFile = new WorkWithFile();
        LinkedHashMap<String, String> characters = workWithFile.readFromCsv();
        if (characters.isEmpty()) {
            System.out.println("Программа завершена: нет данных для обработки");
        } else {
            workWithFile.saveToFile(characters, OUTPUT_FILE);
            CRUD crud = new CRUD(characters);
            crud.demonstrateCrud();
        }
    }
}