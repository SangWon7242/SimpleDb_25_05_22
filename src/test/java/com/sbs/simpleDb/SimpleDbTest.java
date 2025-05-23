package com.sbs.simpleDb;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class SimpleDbTest {
  private SimpleDb simpleDb;
  
  // beforeAll : 다른 테스트 케이스 실행 전에 제일 먼전 실행되는 메서드
  @BeforeAll
  public void beforeAll() {
    simpleDb = new SimpleDb("localhost", "root", "", "simpleDb_test");
    simpleDb.setDevMode(true);
  }

  @Test
  @DisplayName("t1: 데이터 베이스 연결 테스트")
  public void t1() {
    assertTrue(simpleDb.isConnected(), "데이터베이스 연결이 실패했습니다.");
    System.out.println("DB 연결 성공");
  }

  // 테스트 종료 후 연결 종료를 위한 메서드
  @AfterAll
  public void afterAll() {
    if (simpleDb != null) {
      simpleDb.close();
    }
  }
}
