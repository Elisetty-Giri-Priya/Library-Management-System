package com.example.Library.model;

public class Book {
  private Integer id;
  private String title;
  private String author;
  private String isbn;
  private int totalCopies;
  private int availableCopies;

  public Book() {}
  public Book(Integer id, String title, String author, String isbn, int total, int avail) {
    this.id = id; this.title = title; this.author = author; this.isbn = isbn;
    this.totalCopies = total; this.availableCopies = avail;
  }

  public Integer getId() { return id; }
  public void setId(Integer id) { this.id = id; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getAuthor() { return author; }
  public void setAuthor(String author) { this.author = author; }
  public String getIsbn() { return isbn; }
  public void setIsbn(String isbn) { this.isbn = isbn; }
  public int getTotalCopies() { return totalCopies; }
  public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
  public int getAvailableCopies() { return availableCopies; }
  public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

  @Override public String toString() {
    return "Book{id=%d, title='%s', author='%s', isbn='%s', total=%d, avail=%d}"
        .formatted(id, title, author, isbn, totalCopies, availableCopies);
  }
}
