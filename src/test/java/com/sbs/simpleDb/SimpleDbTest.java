package com.sbs.simpleDb;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class SimpleDbTest {
  private SimpleDb simpleDb;
  
  // beforeAll : 다른 테스트 케이스 실행 전에 제일 먼전 실행되는 메서드
  @BeforeAll
  public void beforeAll() {
    simpleDb = new SimpleDb("localhost", "root", "", "simpleDb_test");
  }

  @Test
  public void t1() {

  }
}
