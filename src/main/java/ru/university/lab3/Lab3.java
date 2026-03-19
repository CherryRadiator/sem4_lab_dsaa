package ru.university.lab3;

import java.util.Random;
import java.util.Scanner;

public class Lab3 {

  private static final int MEMORY_SIZE = 1000;
  private static Integer[] segment1 = new Integer[MEMORY_SIZE];
  private static Double[] segment2 = new Double[MEMORY_SIZE];

  private static int freeIndexSeg1 = 0;
  private static int freeIndexSeg2 = 0;

  static class Pointer<T> {
    int address;
    int segmentId;
    boolean isNull;

    public Pointer() {
      this.isNull = true;
      this.address = -1;
      this.segmentId = -1;
    }

    public static <T> Pointer<T> NewPointer(Class<T> type) {
      Pointer<T> p = new Pointer<>();
      if (type == Integer.class) {
        if (freeIndexSeg1 >= MEMORY_SIZE) {
          System.out.println("[ERROR] Insufficient memory in segment 1!");
          return p;
        }
        p.segmentId = 1;
        p.address = freeIndexSeg1++;
        p.isNull = false;
      } else if (type == Double.class) {
        if (freeIndexSeg2 >= MEMORY_SIZE) {
          System.out.println("[ERROR] Insufficient memory in segment 2!");
          return p;
        }
        p.segmentId = 2;
        p.address = freeIndexSeg2++;
        p.isNull = false;
      }
      return p;
    }

    public static <T> void NewPointerLeak(Pointer<T> p, Class<T> type) {
      if (!p.isNull) {
        System.out.println("[LEAK] The pointer is already points to the address " + p.address
            + " (segment " + p.segmentId + ").");
        System.out.println("Allocate new memory without freeing the old...");
        int oldAddr = p.address;
        int oldSeg = p.segmentId;

        Pointer<T> fresh = NewPointer(type);
        p.address = fresh.address;
        p.segmentId = fresh.segmentId;
        p.isNull = fresh.isNull;

        System.out.println("[LEAK] New address: " + p.address
            + ". Old address " + oldAddr
            + " (segment " + oldSeg + ") is now unavailabel - leak!");
      } else {
        Pointer<T> fresh = NewPointer(type);
        p.address = fresh.address;
        p.segmentId = fresh.segmentId;
        p.isNull = fresh.isNull;
      }
    }

    public static <T> T ReadPointer(Pointer<T> p) {
      if (p == null || p.isNull) {
        System.out.println("[ERROR] Attempt to read null pointer!");
        return null;
      }
      if (p.segmentId == 1)
        return (T) segment1[p.address];
      if (p.segmentId == 2)
        return (T) segment2[p.address];
      return null;
    }

    public static <T> void WritePointer(Pointer<T> p, T value) {
      if (p == null || p.isNull) {
        System.out.println("[ERROR] Attempt to write null pointer!");
        return;
      }
      if (p.segmentId == 1)
        segment1[p.address] = (Integer) value;
      if (p.segmentId == 2)
        segment2[p.address] = (Double) value;
    }

    public static <T> void SetPointer(Pointer<T> dest, Pointer<T> src) {
      if (src == null || src.isNull) {
        dest.isNull = true;
        return;
      }
      WritePointer(dest, ReadPointer(src));
    }

    public static <T> void FreePointer(Pointer<T> p) {
      if (p != null && !p.isNull) {
        if (p.segmentId == 1)
          segment1[p.address] = null;
        if (p.segmentId == 2)
          segment2[p.address] = null;
        p.isNull = true;
        p.address = -1;
        p.segmentId = -1;
      }
    }
  }

  private static int readInt(Scanner sc, String prompt) {
    while (true) {
      System.out.print(prompt);
      if (sc.hasNextInt()) {
        int v = sc.nextInt();
        sc.nextLine();
        return v;
      }
      System.out.println("[ERROR] Must be integer. Try again.");
      sc.nextLine();
    }
  }

  private static int readIntRange(Scanner sc, String prompt, int min, int max) {
    while (true) {
      int v = readInt(sc, prompt);
      if (v >= min && v <= max)
        return v;
      System.out.println("[ERROR] Enter number from " + min + " up to " + max + ".");
    }
  }

  @SuppressWarnings("unchecked")
  private static void task1(Scanner sc) {
    System.out.println("\nTask 1: Sum of elements between 1 and 2 positive elements");
    int n = readIntRange(sc, "Size of array N (1–" + MEMORY_SIZE + "): ", 1, MEMORY_SIZE);

    Pointer<Integer>[] arr = new Pointer[n];
    Random rnd = new Random();

    System.out.print("Array: ");
    for (int i = 0; i < n; i++) {
      arr[i] = Pointer.NewPointer(Integer.class);
      Pointer.WritePointer(arr[i], rnd.nextInt(21) - 10);
      System.out.printf("%4d", Pointer.ReadPointer(arr[i]));
    }
    System.out.println();

    int firstPos = -1;
    int secondPos = -1;

    for (int i = 0; i < n; i++) {
      Integer val = Pointer.ReadPointer(arr[i]);
      if (val != null && val > 0) {
        if (firstPos == -1) {
          firstPos = i;
        } else if (secondPos == -1) {
          secondPos = i;
          break;
        }
      }
    }

    if (firstPos == -1 || secondPos == -1) {
      System.out.println("Result: array has less than two positive elements.");
    } else {
      System.out.println("First positive: index=" + firstPos
          + ", value=" + Pointer.ReadPointer(arr[firstPos]));
      System.out.println("Second positive: index=" + secondPos
          + ", value=" + Pointer.ReadPointer(arr[secondPos]));

      Pointer<Integer> sumPtr = Pointer.NewPointer(Integer.class);
      Pointer.WritePointer(sumPtr, 0);

      for (int i = firstPos + 1; i < secondPos; i++) {
        Integer curr = Pointer.ReadPointer(arr[i]);
        Integer sum = Pointer.ReadPointer(sumPtr);
        Pointer.WritePointer(sumPtr, sum + curr);
      }

      System.out.println("Sum between them: " + Pointer.ReadPointer(sumPtr));
      Pointer.FreePointer(sumPtr);
    }

    for (int i = 0; i < n; i++)
      Pointer.FreePointer(arr[i]);
  }

  @SuppressWarnings("unchecked")
  private static void task2(Scanner sc) {
    System.out.println("\nTask 2: Element with the maximum absolute value");
    int n = readIntRange(sc, "Size of array N (1–" + MEMORY_SIZE + "): ", 1, MEMORY_SIZE);

    Pointer<Double>[] arr = new Pointer[n];
    Random rnd = new Random();

    System.out.print("Array:");
    for (int i = 0; i < n; i++) {
      arr[i] = Pointer.NewPointer(Double.class);
      double val = Math.round((rnd.nextDouble() * 200 - 100) * 100.0) / 100.0;
      Pointer.WritePointer(arr[i], val);
      System.out.printf(" %7.2f", Pointer.ReadPointer(arr[i]));
    }
    System.out.println();

    Pointer<Double> maxPtr = Pointer.NewPointer(Double.class);
    Pointer.SetPointer(maxPtr, arr[0]); // копируем первый элемент

    for (int i = 1; i < n; i++) {
      Double curr = Pointer.ReadPointer(arr[i]);
      Double maxVal = Pointer.ReadPointer(maxPtr);
      if (curr != null && Math.abs(curr) > Math.abs(maxVal)) {
        Pointer.SetPointer(maxPtr, arr[i]);
      }
    }

    System.out.printf("Maximum absolute value: %.2f%n", Pointer.ReadPointer(maxPtr));
    Pointer.FreePointer(maxPtr);
    for (int i = 0; i < n; i++)
      Pointer.FreePointer(arr[i]);
  }

  private static void demoLeak() {
    System.out.println("\nMemory leak demonstration");

    Pointer<Integer> p = Pointer.NewPointer(Integer.class);
    Pointer.WritePointer(p, 42);
    System.out.println("Pointer created -> address=" + p.address
        + ", segment=" + p.segmentId
        + ", value=" + Pointer.ReadPointer(p));

    System.out.println();
    Pointer.NewPointerLeak(p, Integer.class);

    System.out.println("Value at the new address: " + Pointer.ReadPointer(p)
        + " (memory not initialized)");

    Pointer.FreePointer(p);
    System.out.println("A new address has been released. The old address is irretrievably lost.");
  }

  public static void start(Scanner sc) {
    System.out.println("Lab3  -  Pointers");
    System.out.println("Segment 1: Integer (task 1)");
    System.out.println("Segment 2: Double  (task 2)");

    boolean running = true;
    while (running) {
      System.out.println("\n  Menu:");
      System.out.println("   1 - Task 1: sum betweew two positive");
      System.out.println("   2 - Task 2: maximum by absolute");
      System.out.println("   3 - Memory leak demonstreation");
      System.out.println("   0 - Exit");
      int choice = readIntRange(sc, "Choice: ", 0, 3);
      switch (choice) {
        case 1 -> task1(sc);
        case 2 -> task2(sc);
        case 3 -> demoLeak();
        case 0 -> running = false;
      }
    }
  }
}
