package ru.otus.hw09.sessionmanager;

import org.junit.Test;
import ru.otus.hw09.api.sessionmanager.SessionManager;
import ru.otus.hw09.api.sessionmanager.SessionManagerException;

import java.sql.Connection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class SessionManagerTest {

  @Test
  public void getConnection1() {
    SessionManager connectionImp = new SessionManagerImp();
    Connection connection = connectionImp.getConnection();
    assertNotNull(connection);
  }

  @Test
  public void getConnection2() {
    assertThrows(IllegalArgumentException.class, () -> new SessionManagerImp("", true));
  }

  @Test
  public void getConnection3() {
    SessionManager connectionImp = new SessionManagerImp("jdbc:h2:mem:", true);
    Connection connection = connectionImp.getConnection();
    assertNotNull(connection);
  }

  @Test
  public void getConnection4() {
    assertThrows(SessionManagerException.class, () -> new SessionManagerImp("123", true));
  }


  @Test
  public void commitSession() {
    assertThrows(SessionManagerException.class,
        () -> {
          SessionManager sessionManagerImp = new SessionManagerImp("jdbc:h2:mem:", false);
          sessionManagerImp.close();
          sessionManagerImp.commitSession();
        });

  }

  @Test
  public void rollbackSession() {
    assertThrows(SessionManagerException.class,
        () -> {
          SessionManager sessionManagerImp = new SessionManagerImp("jdbc:h2:mem:", false);
          sessionManagerImp.close();
          sessionManagerImp.rollbackSession();
        });

  }
}
