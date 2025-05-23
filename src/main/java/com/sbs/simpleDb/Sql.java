package com.sbs.simpleDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Sql {
  private final SimpleDb simpleDb;
  private final StringBuilder sql;
  private final List<Object> params;

  public Sql(SimpleDb simpleDb) {
    this.simpleDb = simpleDb;
    this.sql = new StringBuilder();
    this.params = new ArrayList<>();
  }

  public Sql append(String sqlBit, Object... args) {
    sql.append(" ").append(sqlBit);

    if (args != null) {
      for (Object arg : args) {
        params.add(arg);
      }
    }

    if (simpleDb.isDevMode()) {
      System.out.println("== SQL 생성 과정 ==");
      System.out.println("sql : " + sql);
      System.out.println("params : " + params);
    }

    return this;
  }

  public long insert() {
    try {
      Connection conn = simpleDb.getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

      for (int i = 0; i < params.size(); i++) {
        stmt.setObject(i + 1, params.get(i));
      }

      stmt.executeUpdate();

      // auto_increment 값 가져오기
      ResultSet rs = stmt.getGeneratedKeys();
      if (rs.next()) {
        return rs.getLong(1);
      }

      return -1;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
