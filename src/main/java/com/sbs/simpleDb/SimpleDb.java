package com.sbs.simpleDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SimpleDb {
  private Connection conn;

  public SimpleDb(String host, String username, String password, String dbName) {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      String url = "jdbc:mysql://%s:3306/%s?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeBehavior=convertToNull".formatted(host, dbName);

      conn = DriverManager.getConnection(url, username, password);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("MySQL JDBC 드라이버를 찾을 수 없습니다.", e);
    } catch (SQLException e) {
      System.out.println("DB 연결 실패!");
      System.out.println("에러 메시지: " + e.getMessage());
      throw new RuntimeException("DB 연결 실패!", e);
    }
  }

  public void setDevMode(boolean mode) {

  }

  public void run(String sql) {
    try {
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(sql);
    } catch (SQLException e) {
      throw new RuntimeException("SQL 실행 중 오류 발생", e);
    }
  }

  public boolean isConnected() {
    try {
      return conn != null && !conn.isClosed();
    } catch (SQLException e) {
      return false;
    }
  }

  public void close() {
    if (conn != null) {
      try {
        conn.close();
        System.out.println("DB 연결이 종료되었습니다.");
      } catch (SQLException e) {
        System.out.println("DB 연결 종료 중 오류 발생");
        e.printStackTrace();
      }
    }
  }
}
