package com.sbs.simpleDb;

import org.junit.jupiter.api.*;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class SimpleDbTest {
  private SimpleDb simpleDb;
  
  // beforeAll : 다른 테스트 케이스 실행 전에 제일 먼저 실행되는 메서드
  @BeforeAll
  public void beforeAll() {
    simpleDb = new SimpleDb("localhost", "root", "", "simpleDb_test");
    simpleDb.setDevMode(true);

    createArticleTable();
  }

  @BeforeEach
  public void beforeEach() {
    truncateArticleTable();
    makeArticleTestData();
  }

  private void createArticleTable() {
    simpleDb.run("DROP TABLE IF EXISTS article");

    simpleDb.run("""
        CREATE TABLE article (
            id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
            createDate DATETIME NOT NULL,
            modifiedDate DATETIME NOT NULL,
            subject VARCHAR(100) NOT NULL,
            content TEXT NOT NULL,
            isBlind BIT(1) NOT NULL DEFAULT 0
        );
        """);
  }

  private void truncateArticleTable() {
    simpleDb.run("TRUNCATE article");
  }

  private void makeArticleTestData() {
    IntStream.rangeClosed(1, 6).forEach(
        no -> {
          boolean isBlind = no > 3;
          String subject = "제목 %s".formatted(no);
          String content = "내용 %s".formatted(no);

          simpleDb.run("""
            INSERT INTO article
            SET createDate = NOW(),
            modifiedDate = NOW(),
            subject = ?,
            content = ?,
            isBlind = ?;
            """, subject, content, isBlind);
        }
    );
  }

  @Test
  public void t1() {
  }

  // 테스트 종료 후 연결 종료를 위한 메서드
  @AfterAll
  public void afterAll() {
    if (simpleDb != null) {
      simpleDb.close();
    }
  }
}
