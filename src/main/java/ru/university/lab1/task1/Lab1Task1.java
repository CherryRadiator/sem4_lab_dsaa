package ru.university.lab1.task1;

import java.util.Scanner;
import java.util.Set;

public class Lab1Task1 {
  public void start(Scanner scanner) {
    final Set<Character> TARGET_CHARS = Set.of('№', '!', '»', ';', ',', '.', '%', ':', '?', '@', '#', '$', '^', '&',
        '*',
        '(', ')');

    System.out.println("Lab1Task1 started\n");

    this.printset(TARGET_CHARS);
    System.out.println("Enter your string: ");

    String userInput;
    if (scanner.hasNextLine()) {
      userInput = scanner.nextLine();
    } else {
      System.out.println("Input stream closed. Exiting...");
      return;
    }
    if (userInput.equals("")) {
      System.out.println("You doesn't type anything... Suspicious");
    }

    boolean containsTargetChar = false;
    for (char c : userInput.toCharArray()) {
      if (TARGET_CHARS.contains(c)) {
        System.out.println("Your string contains character: \"" + c + "\". Suspicious...");
        containsTargetChar = true;
      }
    }
    if (!containsTargetChar) {
      System.out.println("There is nothing suspicious in your string...");
    }

  }

  private void printset(Set<Character> set) {
    System.out.println("Set: " + set + "\nNumber of elements in set: " + set.size());
  }
}
