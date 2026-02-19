package com.goncharova.labs;
import java.util.LinkedHashMap;
public class OutputToTheConsole
{
    public void printCharacter(LinkedHashMap<String, String> characters, String name)
    {
        String location = characters.get(name);
        if (location != null)
        {
            System.out.println(name + " -> " + location);
        }
        else
        {
            System.out.println("Персонаж " + name + " не найден");
        }
    }
}