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
            System.out.println("Заголовок: " + header);
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 8) continue;
                String name = parts[1].trim();
                String location = parts[7].trim();
                characters.put(name, location);
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Файл не найден: " + filePath);
        }
        catch (IOException e)
        {
            System.out.println("Ошибка чтения файла:" + e.getMessage());
        }
        return characters;
    }
    public void saveToFile(LinkedHashMap<String, String> characters, String fileName)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            writer.write("name -> location/name");
            writer.newLine();
            for (Map.Entry<String, String> entry : characters.entrySet())
            {
                writer.write(entry.getKey() + " -> " + entry.getValue());
                writer.newLine();
            }
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("Ошибка записи файла:" + e.getMessage());
        }
    }
}