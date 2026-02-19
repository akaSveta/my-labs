package com.goncharova.labs;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
public class WorkWithFile
{
    public LinkedHashMap<String, String> readFromCsv()
    {
        LinkedHashMap<String, String> characters = new LinkedHashMap<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("characters.csv");
        if (inputStream == null)
        {
            System.out.println("Файл characters.csv не найден в папке resources!");
            System.out.println("Положите файл в: src/main/resources");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
        {
            String header = reader.readLine();
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                String name = parts[1].trim();
                String location = parts[7].trim();
                characters.put(name, location);
            }
        }
        catch (IOException e)
        {
            System.out.println("Ошибка чтения файла:" + e.getMessage());
            e.printStackTrace();
        }
        return characters;
    }
    public void saveToFile(LinkedHashMap<String, String> characters, String fileName)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            writer.write("name -> location/name");
            writer.newLine();
            for (Map.Entry<String, String> entry : characters.entrySet())
            {
                writer.write(entry.getKey() + " -> " + entry.getValue());
                writer.newLine();
            }
            File file = new File(fileName);
        }
        catch (IOException e)
        {
            System.out.println("Ошибка записи файла:" + e.getMessage());
            e.printStackTrace();
        }
    }
}