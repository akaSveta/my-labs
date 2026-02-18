package com.goncharova.labs;
import java.util.LinkedHashMap;
public class Lab1Variant6
{
    private static final String INPUT_FILE = "C:\\Users\\sveta\\IdeaProjects\\my-labs\\lab-1\\src\\main\\resources\\characters.csv";
    private static final String OUTPUT_FILE = "result.txt";
    public static void main(String[] args)
    {
        WorkWithFile workWithFile = new WorkWithFile();
        LinkedHashMap<String, String> characters = workWithFile.readFromCsv(INPUT_FILE);
        if (characters.isEmpty())
        {
            System.out.println("Программа завершена: нет данных для обработки.");
            return;
        }
        OutputToTheConsole printer = new OutputToTheConsole();
        printer.printAllCharacters(characters);
        workWithFile.saveToFile(characters, OUTPUT_FILE);
    }
}