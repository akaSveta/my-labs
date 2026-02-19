package com.goncharova.labs;
import java.util.LinkedHashMap;
public class CRUD
{
    private LinkedHashMap<String, String> characters;
    public CRUD(LinkedHashMap<String, String> characters)
    {
        this.characters = characters;
    }
    public void create(String name, String location)
    {
        if (name == null || name.trim().isEmpty())
        {
            System.out.println("Имя не может быть пустым!");
            return;
        }
        if (characters.containsKey(name))
        {
            System.out.println("Персонаж " + name + " уже существует!");
            return;
        }
        characters.put(name, location);
        System.out.println("Добавлен:" + name + " -> " + location);
    }
    public void read(String name)
    {
        String location = characters.get(name);
        if (location != null)
        {
            System.out.println("Найден: " + name + " -> " + location);
        }
        else
        {
            System.out.println("Персонаж " + name + " не найден");
        }
    }
    public void update(String name, String newLocation)
    {
        if (!characters.containsKey(name))
        {
            System.out.println("Персонаж " + name + " не существует");
            return;
        }
        String oldLocation = characters.get(name);
        characters.put(name, newLocation);
        System.out.println("Обновлен: " + name);
        System.out.println("Было: " + oldLocation);
        System.out.println("Стало: " + newLocation);
    }
    public void delete(String name)
    {
        if (!characters.containsKey(name))
        {
            System.out.println("Персонаж " + name + " не найден");
            return;
        }
        characters.remove(name);
        System.out.println("Удален: " + name);
    }
    public void demonstrateCrud()
    {
        System.out.println("Create(добавление)");
        create("Тестовый Персонаж", "Тестовая Локация");
        System.out.println("Read(чтение)");
        read("Rick Sanchez");
        System.out.println("Update(обновление)");
        update("Morty", "Новая Локация Морти");
        System.out.println("Delete(удаление)");
        delete("Тестовый Персонаж");
    }
}