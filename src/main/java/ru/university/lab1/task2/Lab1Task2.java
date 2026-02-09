package ru.university.lab1.task2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Lab1Task2 {
  public void start(Scanner scanner) {
    final HashSet<Character> TARGET_CHARS = new HashSet<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'X', 'Y', 'Z'));

    HashSet<Character> resultSet = new HashSet<>();

    System.out.println("Lab1Task2 started\n");

    String userInput;

    boolean isValidInput = false;
    while (!isValidInput) {
      if (scanner.hasNextLine()) {
        userInput = scanner.nextLine();
      } else {
        System.out.println("Input stream closed. Exiting...");
        return;
      }

      if (userInput.equals("")) {
        System.out.println("Empty lines are not allowed");
      } else {
        isValidInput = true;
      }

      resultSet = buildSet(TARGET_CHARS, userInput);

      printset(resultSet);
    }

  }

  private HashSet<Character> buildSet(HashSet<Character> targetSet, String string) {
    HashSet<Character> resultSet = new HashSet<>();
    for (char c : string.toCharArray()) {
      if (targetSet.contains(c)) {
        resultSet.add(c);
      }
    }
    return resultSet;
  }

  private void printset(HashSet<Character> set) {
    System.out.println("Set: " + set + "\n size of set: " + set.size());
    return;
  }
}
