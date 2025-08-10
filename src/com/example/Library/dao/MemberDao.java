package com.example.Library.dao;

import com.example.Library.config.Db;
import com.example.Library.model.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDao {
  public int create(Member m) throws SQLException {
    String sql = "INSERT INTO members(name, email) VALUES (?,?)";
    try (Connection con = Db.getConnection();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, m.getName());
      ps.setString(2, m.getEmail());
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getInt(1); }
    }
    return -1;
  }

  public List<Member> listAll() throws SQLException {
    String sql = "SELECT id,name,email FROM members ORDER BY id";
    List<Member> out = new ArrayList<>();
    try (Connection con = Db.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        out.add(new Member(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
      }
    }
    return out;
  }
}
