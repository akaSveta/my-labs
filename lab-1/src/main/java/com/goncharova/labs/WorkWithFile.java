package com.goncharova.labs;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
public class WorkWithFile
{
    public LinkedHashMap<String, String> readFromCsv(String filePath)
    {
        LinkedHashMap<String, String> characters = new LinkedHashMap<>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String header = reader.readLine();
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null)
            {
                lineNumber++;
                String[] parts = line.split(",");
                String name = parts[1];
                String location = parts[7];
                characters.put(name, location);
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Ошибка:файл не найден!");
        }
        catch (IOException e)
        {
            System.out.println("Ошибка при чтении файла:" + e.getMessage());
        }
        return characters;
    }
    public void saveToFile(LinkedHashMap<String, String> characters, String fileName)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write("name -> location/name");
            writer.newLine(); // переход на новую строку
            for (Map.Entry<String, String> entry : characters.entrySet())
            {
                writer.write(entry.getKey() + " -> " + entry.getValue());
                writer.newLine();
            }
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("Ошибка при сохранении файла:" + e.getMessage());
        }
    }
}
