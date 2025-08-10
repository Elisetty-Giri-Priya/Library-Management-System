package com.example.Library.dao;

import com.example.Library.config.Db;

import java.sql.*;
import java.time.LocalDate;

public class LoanDao {
  public boolean borrowBook(int bookId, int memberId, int days) throws SQLException {
    String insertLoan = "INSERT INTO loans(book_id, member_id, loan_date, due_date) VALUES (?,?,?,?)";
    try (Connection con = Db.getConnection()) {
      con.setAutoCommit(false);
      try {
        // Decrement availability first
        int updated = new BookDao().updateAvailable(bookId, -1);
        if (updated == 0) { con.rollback(); return false; }

        try (PreparedStatement ps = con.prepareStatement(insertLoan)) {
          LocalDate today = LocalDate.now();
          ps.setInt(1, bookId);
          ps.setInt(2, memberId);
          ps.setDate(3, Date.valueOf(today));
          ps.setDate(4, Date.valueOf(today.plusDays(days)));
          ps.executeUpdate();
        }
        con.commit();
        return true;
      } catch (SQLException e) {
        con.rollback();
        throw e;
      } finally {
        con.setAutoCommit(true);
      }
    }
  }

  public boolean returnBook(int loanId) throws SQLException {
    String select = "SELECT book_id, return_date FROM loans WHERE id=?";
    String updateLoan = "UPDATE loans SET return_date=? WHERE id=? AND return_date IS NULL";
    try (Connection con = Db.getConnection()) {
      con.setAutoCommit(false);
      try (PreparedStatement ps = con.prepareStatement(select)) {
        ps.setInt(1, loanId);
        try (ResultSet rs = ps.executeQuery()) {
          if (!rs.next()) { con.rollback(); return false; }
          int bookId = rs.getInt("book_id");
          Date alreadyReturned = rs.getDate("return_date");
          if (alreadyReturned != null) { con.rollback(); return false; }

          try (PreparedStatement up = con.prepareStatement(updateLoan)) {
            up.setDate(1, new Date(System.currentTimeMillis()));
            up.setInt(2, loanId);
            if (up.executeUpdate() == 0) { con.rollback(); return false; }
          }

          int updated = new BookDao().updateAvailable(bookId, +1);
          if (updated == 0) { con.rollback(); return false; }

          con.commit();
          return true;
        }
      } catch (SQLException e) {
        con.rollback();
        throw e;
      } finally {
        con.setAutoCommit(true);
      }
    }
  }
}
