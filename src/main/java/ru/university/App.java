package ru.university;

import java.util.Scanner;

import ru.university.lab1.task1.Lab1Task1;
import ru.university.lab1.task2.Lab1Task2;

/**
 * Hello world!
 */
public class App {
  public static void main(String[] args) {
    System.out.println("Hello World!");

    Scanner scanner = new Scanner(System.in);

    System.out.println("Before we start, what lab is it? (choose a number):");
    System.out.println("1. Sets");

    if (!scanner.hasNextLine())
      return;
    String labInput = scanner.nextLine();
    byte labChoice = -1;

    try {
      labChoice = Byte.parseByte(labInput);
    } catch (NumberFormatException e) {
      System.out.println("Invalid number format.");
      return;
    }

    if (labChoice == 1) {
      System.out.println("Well, what task is it? (choose a number)");
      System.out.println("1. (26 variant)");
      System.out.println("2. (26 variant)");

      if (!scanner.hasNextLine())
        return;
      String taskInput = scanner.nextLine();
      byte taskChoice = -1;

      try {
        taskChoice = Byte.parseByte(taskInput);
      } catch (NumberFormatException e) {
        System.out.println("Invalid number format.");
        return;
      }

      if (taskChoice == 1) {
        Lab1Task1 lab1Task1 = new Lab1Task1();
        lab1Task1.start(scanner);
      } else if (taskChoice == 2) {
        Lab1Task2 lab1Task2 = new Lab1Task2();
        lab1Task2.start(scanner);
      }

    }

    scanner.close();
  }
}
