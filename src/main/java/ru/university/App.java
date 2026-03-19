package ru.university;

import java.util.Scanner;

import ru.university.lab1.task1.Lab1Task1;
import ru.university.lab1.task2.Lab1Task2;
import ru.university.lab2.task1.Lab2Task1;
import ru.university.lab2.task2.Lab2Task2;
import ru.university.lab3.Lab3;

/**
 * Hello world!
 */
public class App {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    if (args.length == 0) {

      System.out.println("Before we start, what lab is it? (choose a number):");
      System.out.println("1. Sets");
      System.out.println("2. Files");
      System.out.println("3. Pointers");

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

      } else if (labChoice == 2) {
        System.out.println("Well, What task is it? (choose a number)");
        System.out.println("1. (27 variant)");
        System.out.println("2. (27 variant)");

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
          Lab2Task1 lab2Task1 = new Lab2Task1();
          lab2Task1.start(scanner);
        } else if (taskChoice == 2) {
          Lab2Task2 lab2Task2 = new Lab2Task2();
          lab2Task2.start(scanner);
        }
      } else if (labChoice == 3) {
        Lab3.start(scanner);
      }

    } else {
      byte labChoice = Byte.parseByte(args[0]);
      byte taskChoice = Byte.parseByte(args[1]);
      switch (labChoice) {
        case 1:
          switch (taskChoice) {
            case 1:
              Lab1Task1 lab1Task1 = new Lab1Task1();
              lab1Task1.start(scanner);
              break;
            case 2:
              Lab1Task2 lab1Task2 = new Lab1Task2();
              lab1Task2.start(scanner);
              break;
          }
        case 2:
          switch (taskChoice) {
            case 1:
              Lab2Task1 lab2Task1 = new Lab2Task1();
              lab2Task1.start(scanner);
              break;
            case 2:
              Lab2Task2 lab2Task2 = new Lab2Task2();
              lab2Task2.start(scanner);
              break;
          }
      }
    }
    scanner.close();
  }
}
