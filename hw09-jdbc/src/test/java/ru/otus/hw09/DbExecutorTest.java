package ru.otus.hw09;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import ru.otus.hw09.model.User;
import ru.otus.hw09.service.DbExecutor;
import ru.otus.hw09.service.DbExecutorException;
import ru.otus.hw09.service.DbExecutorImp;
import ru.otus.hw09.service.ParseObjectOrClassException;
import ru.otus.hw09.sessionmanager.SessionManagerImp;

import java.sql.Connection;

import static org.junit.Assert.assertThrows;

public class DbExecutorTest {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserTest.class);

    private Connection connection = new SessionManagerImp().getConnection();
    private DbExecutor dbExecutor = new DbExecutorImp();

    @Test
    public void createTable1() {
        assertThrows(ParseObjectOrClassException.class, ()-> { dbExecutor.createTable(connection, String.class); });
    }

    @Test
    public void createTable2() {
        assertThrows(IllegalArgumentException.class, ()-> {  dbExecutor.createTable(null, String.class); });
    }


    @Test
    public void insert1() {
        assertThrows(IllegalArgumentException.class, ()-> { dbExecutor.insert(null, String.class); });
    }

    @Test
    public void insert2() {
        assertThrows(DbExecutorException.class, ()-> { dbExecutor.insert(connection, String.class); });
    }


    @Test
    public void insert3() {
        assertThrows(DbExecutorException.class, ()-> { dbExecutor.insert(connection, User.class); });
    }

    @Test
    public void insert4() {
        assertThrows(DbExecutorException.class, ()-> { dbExecutor.insert(connection, new User()); });
    }

    @Test
    public void select1() {
        assertThrows(IllegalArgumentException.class, ()-> { dbExecutor.select(null, 0,null); });
    }

    @Test
    public void select2() {
        assertThrows(DbExecutorException.class, ()-> { dbExecutor.select(connection, 1,String.class); });
    }

    @Test
    public void select3() {
        assertThrows(DbExecutorException.class, ()-> { dbExecutor.select(connection, 1, User.class); });
    }




}
