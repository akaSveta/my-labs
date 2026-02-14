package com.goncharova.labs;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Lab1Variant6
{
    private static final String input_file = "C:\\Users\\sveta\\IdeaProjects\\my-labs\\lab-1\\src\\main\\resources\\characters.csv";
    private static final String output_file = "result_characters.txt";
    public static void main(String[] args)
    {
        LinkedHashMap<String, String> charactersLocation = new LinkedHashMap<>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(input_file));
            String header = reader.readLine();
            String line;
            int lineNumber = 1;
            int counterAddedRecords = 0;
            while ((line = reader.readLine()) != null)
            {
                lineNumber++;
                if (line.trim().isEmpty())
                {
                    continue;
                }
                String[] columns = line.split(",");
                if (columns.length < 8)
                {
                    System.out.println("Строка " + lineNumber + " пропущена: недостаточно данных");
                    continue;
                }
                String charactersName = columns[1].trim();
                String locationName = columns[7].trim();
                charactersLocation.put(charactersName, locationName);
                counterAddedRecords++;
            }
            reader.close();
            System.out.println("Всего прочитано и добавлено строк: " + counterAddedRecords);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Ошибка: файл " + input_file + " не найден!");
            System.out.println("Положите файл characters.csv в папку resources");
            return;
        }
        catch (IOException e)
        {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }
        int count = 0;
        for (Map.Entry<String, String> entry : charactersLocation.entrySet())
        {
            count++;
            System.out.println(count + ". " + entry.getKey() + " -> " + entry.getValue());
        }
        System.out.println("Всего записей: " + count);
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(output_file));
            writer.write("name -> location/name");
            writer.newLine();
            for (Map.Entry<String, String> entry: charactersLocation.entrySet())
            {
                writer.write(entry.getKey() + " -> " + entry.getValue());
                writer.newLine();
            }
            writer.close();
            System.out.println("Результат сохранён в файл: " + output_file);
        }
        catch (IOException e)
        {
            System.out.println("Ошибка при записи файла: " + e.getMessage());
        }
    }
}