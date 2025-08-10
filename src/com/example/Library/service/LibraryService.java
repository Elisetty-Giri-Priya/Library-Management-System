package com.example.Library.service;

import com.example.Library.dao.BookDao;
import com.example.Library.dao.LoanDao;
import com.example.Library.dao.MemberDao;
import com.example.Library.model.Book;
import com.example.Library.model.Member;

import java.sql.SQLException;
import java.util.List;

public class LibraryService {
  private final BookDao bookDao = new BookDao();
  private final MemberDao memberDao = new MemberDao();
  private final LoanDao loanDao = new LoanDao();

  // Books
  public int addBook(String title, String author, String isbn, int copies) throws SQLException {
    return bookDao.create(new Book(null, title, author, isbn, copies, copies));
  }
  public List<Book> listBooks() throws SQLException { return bookDao.listAll(); }

  // Members
  public int addMember(String name, String email) throws SQLException {
    return memberDao.create(new Member(null, name, email));
  }
  public List<Member> listMembers() throws SQLException { return memberDao.listAll(); }

  // Loans
  public boolean borrow(int bookId, int memberId, int days) throws SQLException {
    return loanDao.borrowBook(bookId, memberId, days);
  }
  public boolean returnLoan(int loanId) throws SQLException {
    return loanDao.returnBook(loanId);
  }
}
