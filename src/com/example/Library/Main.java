package com.example.Library;

import com.example.Library.service.LibraryService;

public class Main {
  public static void main(String[] args) throws Exception {
    var svc = new LibraryService();

    System.out.println("Books:");
    svc.listBooks().forEach(System.out::println);

    System.out.println("\nBorrowing book #1 for member #1 (7 days)...");
    boolean ok = svc.borrow(1, 1, 7);
    System.out.println("Borrow success? " + ok);

    System.out.println("\nBooks after borrow:");
    svc.listBooks().forEach(System.out::println);
  }
}
