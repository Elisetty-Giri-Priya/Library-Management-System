package com.example.Library.dao;

import com.example.Library.config.Db;
import com.example.Library.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
  public int create(Book b) throws SQLException {
    String sql = "INSERT INTO books(title, author, isbn, total_copies, available_copies) VALUES (?,?,?,?,?)";
    try (Connection con = Db.getConnection();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, b.getTitle());
      ps.setString(2, b.getAuthor());
      ps.setString(3, b.getIsbn());
      ps.setInt(4, b.getTotalCopies());
      ps.setInt(5, b.getAvailableCopies());
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getInt(1); }
    }
    return -1;
  }

  public List<Book> listAll() throws SQLException {
    String sql = "SELECT id,title,author,isbn,total_copies,available_copies FROM books ORDER BY id";
    List<Book> out = new ArrayList<>();
    try (Connection con = Db.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        out.add(new Book(
          rs.getInt("id"), rs.getString("title"), rs.getString("author"),
          rs.getString("isbn"), rs.getInt("total_copies"), rs.getInt("available_copies")));
      }
    }
    return out;
  }

  /** +1 to return, -1 to borrow (won't go below zero). */
  public int updateAvailable(int bookId, int delta) throws SQLException {
    String sql = "UPDATE books SET available_copies = available_copies + ? WHERE id=? AND available_copies + ? >= 0";
    try (Connection con = Db.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, delta);
      ps.setInt(2, bookId);
      ps.setInt(3, delta);
      return ps.executeUpdate();
    }
  }
}
