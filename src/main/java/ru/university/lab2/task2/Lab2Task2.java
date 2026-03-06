package ru.university.lab2.task2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Lab2Task2 {

  public void start(Scanner scanner) {
    System.out.println("--- Лабораторная работа 2. Задача 2 ---");

    File f1 = new File("resources/lab2task2/f1.txt");
    File f2 = new File("resources/lab2task2/f2.txt");

    System.out.println(
        "Сгенерировать случайные данные для файла f1? (y - да, любая другая клавиша - нет, использовать существующий)");
    String choice = scanner.nextLine().trim().toLowerCase();

    if (choice.equals("y") || choice.equals("н")) {
      System.out.println("Введите количество строк для генерации (в каждой строке будет 6 чисел):");
      int linesCount = getValidPositiveInt(scanner);
      generateRandomFile(f1, linesCount);
    }

    if (!isFileValidAndNotEmpty(f1)) {
      return;
    }

    processPositiveNumbers(f1, f2);
  }

  private int getValidPositiveInt(Scanner scanner) {
    while (true) {
      try {
        int value = Integer.parseInt(scanner.nextLine().trim());
        if (value > 0) {
          return value;
        }
        System.out.println("Ошибка: Введите число больше нуля.");
      } catch (NumberFormatException e) {
        System.out.println("Ошибка формата ввода. Пожалуйста, введите целое число.");
      }
    }
  }

  private void generateRandomFile(File file, int linesCount) {
    Random random = new Random();
    try (FileWriter writer = new FileWriter(file)) {
      for (int i = 0; i < linesCount; i++) {
        for (int j = 0; j < 6; j++) {
          int num = random.nextInt(101) - 50;
          writer.write(num + (j < 5 ? " " : ""));
        }
        writer.write(System.lineSeparator());
      }
      System.out.println("Файл " + file.getName() + " успешно заполнен случайными числами.");
    } catch (IOException e) {
      System.out.println("Ошибка при генерации файла f1.");
      e.printStackTrace();
    }
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

  private void processPositiveNumbers(File in, File out) {
    try (Scanner fileScanner = new Scanner(in);
        FileWriter writer = new FileWriter(out)) {

      while (fileScanner.hasNextLine()) {
        String line = fileScanner.nextLine();
        Scanner lineScanner = new Scanner(line);
        StringBuilder positiveNumbersLine = new StringBuilder();

        while (lineScanner.hasNextInt()) {
          int num = lineScanner.nextInt();
          if (num > 0) {
            positiveNumbersLine.append(num).append(" ");
          }
        }
        lineScanner.close();

        writer.write(positiveNumbersLine.toString().trim() + System.lineSeparator());
      }
      System.out.println("Положительные числа успешно переписаны в файл " + out.getName());

    } catch (FileNotFoundException e) {
      System.out.println("Ошибка чтения файла f1.");
    } catch (IOException e) {
      System.out.println("Ошибка записи в файл f2.");
      e.printStackTrace();
    }
  }
}
