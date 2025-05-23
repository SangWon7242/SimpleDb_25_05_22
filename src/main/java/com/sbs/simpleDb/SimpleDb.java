package com.sbs.simpleDb;

import lombok.Setter;

import java.sql.*;

public class SimpleDb {
  private Connection conn;

  @Setter
  private boolean devMode;

  public SimpleDb(String host, String username, String password, String dbName) {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      String url = "jdbc:mysql://%s:3306/%s?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeBehavior=convertToNull".formatted(host, dbName);

      conn = DriverManager.getConnection(url, username, password);
      System.out.println("DB 연결 성공!");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("MySQL JDBC 드라이버를 찾을 수 없습니다.", e);
    } catch (SQLException e) {
      System.out.println("DB 연결 실패!");
      System.out.println("에러 메시지: " + e.getMessage());
      throw new RuntimeException("DB 연결 실패!", e);
    }
  }

  public void run(String sql, Object... args) {
    System.out.println("SQL : " + sql);

    try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

      for (int i = 0; i < args.length; i++) {
        preparedStatement.setObject(i + 1, args[i]);
      }

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("SQL 실행 중 오류 발생", e);
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
