package ru.university.lab2.task1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lab2Task1 {

  public void start(Scanner scanner) {
    System.out.println("--- Лабораторная работа 2. Задача 1 ---");

    System.out.println("Введите путь к исходному файлу (или нажмите Enter для использования 'originalFile.txt'):");
    String inputPath = scanner.nextLine().trim();
    if (inputPath.isEmpty()) {
      inputPath = "resources/lab2task1/originalFile.txt";
    }

    File originalFile = new File(inputPath);
    if (!isFileValidAndNotEmpty(originalFile)) {
      return;
    }

    System.out.println("Введите путь для нового файла (или нажмите Enter для 'newFile.txt'):");
    String outputPath = scanner.nextLine().trim();
    if (outputPath.isEmpty()) {
      outputPath = "resources/lab2task1/newFile.txt";
    }
    File newFile = new File(outputPath);

    padAndWrite(originalFile, newFile);
  }

  private boolean isFileValidAndNotEmpty(File file) {
    if (!file.exists() || !file.isFile()) {
      System.out.println("Ошибка: Файл '" + file.getPath() + "' не существует.");
      return false;
    }
    if (file.length() == 0) {
      System.out.println("Ошибка: Файл '" + file.getPath() + "' пуст.");
      return false;
    }
    return true;
  }

  private void padAndWrite(File in, File out) {
    List<String> lines = new ArrayList<>();
    int maxLineLength = 0;

    try (Scanner reader = new Scanner(in)) {
      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        lines.add(line);
        if (line.length() > maxLineLength) {
          maxLineLength = line.length();
        }
      }
      System.out.println("Файл " + in.getName() + " успешно прочитан. Максимальная длина строки: " + maxLineLength);
    } catch (FileNotFoundException e) {
      System.out.println("Произошла ошибка при чтении файла.");
      return;
    }

    try (FileWriter writer = new FileWriter(out)) {
      for (String line : lines) {
        int paddingLength = maxLineLength - line.length();
        writer.write(line + "*".repeat(paddingLength) + System.lineSeparator());
      }
      System.out.println("Новый файл " + out.getName() + " успешно создан и заполнен.");
    } catch (IOException e) {
      System.out.println("Произошла ошибка при записи в файл.");
      e.printStackTrace();
    }
  }
}
