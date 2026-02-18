package com.goncharova.labs;

import java.util.LinkedHashMap;
import java.util.Map;

public class OutputToTheConsole {
    public void printAllCharacters(LinkedHashMap<String, String> characters) {
        int number = 0;
        for (Map.Entry<String, String> entry : characters.entrySet()) {
            number++;
            System.out.println(number + ". " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}