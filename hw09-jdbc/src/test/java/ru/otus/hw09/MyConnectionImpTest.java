package ru.otus.hw09;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import ru.otus.hw09.sessionmanager.SessionManager;
import ru.otus.hw09.sessionmanager.SessionManagerException;
import ru.otus.hw09.sessionmanager.SessionManagerImp;

import java.sql.Connection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class MyConnectionImpTest {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MyConnectionImpTest.class);

    @Test
    public void getConnection1(){
        SessionManager connectionImp = new SessionManagerImp();
        Connection connection = connectionImp.getConnection();
        assertNotNull(connection);
    }

    @Test
    public void getConnection2(){
        assertThrows(IllegalArgumentException.class, () -> new SessionManagerImp("",true));
    }

    @Test
    public void getConnection3(){
        SessionManager connectionImp = new SessionManagerImp("jdbc:h2:mem:",true);
        Connection connection = connectionImp.getConnection();
        assertNotNull(connection);
    }

    @Test
    public void getConnection4(){
        assertThrows(SessionManagerException.class, () -> new SessionManagerImp("123",true));
    }


    @Test
    public void commitSession(){
        assertThrows(SessionManagerException.class,
                ()->{
                    SessionManager sessionManagerImp = new SessionManagerImp("jdbc:h2:mem:",false);
                    sessionManagerImp.close();
                    sessionManagerImp.commitSession();
                });

    }

    @Test
    public void rollbackSession(){
        assertThrows(SessionManagerException.class,
                ()->{
                    SessionManager sessionManagerImp = new SessionManagerImp("jdbc:h2:mem:",false);
                    sessionManagerImp.close();
                    sessionManagerImp.rollbackSession();
                });

    }
}
