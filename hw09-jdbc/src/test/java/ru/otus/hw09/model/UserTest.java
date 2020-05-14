package ru.otus.hw09.model;


import org.junit.Test;
import ru.otus.hw09.api.model.Account;
import ru.otus.hw09.api.model.User;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;


public class UserTest {

  @Test
  public void createUser1() {
    User actualUser = new User();
    String expectedUser = "User: id = null, name = null, age = 0";
    assertEquals(expectedUser, actualUser.toString());
  }

  @Test
  public void createUser2() {
    User actualUser = new User("Test", 2);
    String expectedUser = "User: id = null, name = Test, age = 2";
    assertEquals(expectedUser, actualUser.toString());
  }

  @Test
  public void createAccount1() {
    Account actualAcc = new Account();
    String expectedAcc = "Account: no = null, type = null, rest = null";
    assertEquals(expectedAcc, actualAcc.toString());
  }

  @Test
  public void createAccount2() {
    BigDecimal bigDecimal = new BigDecimal(2);
    Account actualAcc = new Account("Test", bigDecimal);
    String expectedAcc = "Account: no = null, type = Test, rest = 2";
    assertEquals(expectedAcc, actualAcc.toString());
  }

}
