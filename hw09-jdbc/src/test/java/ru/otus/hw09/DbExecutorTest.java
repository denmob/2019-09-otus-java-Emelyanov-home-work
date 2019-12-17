package ru.otus.hw09;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import ru.otus.hw09.sessionmanager.SessionManager;
import ru.otus.hw09.sessionmanager.SessionManagerImp;
import ru.otus.hw09.model.Account;
import ru.otus.hw09.model.User;
import ru.otus.hw09.service.DbExecutorImp;
import java.math.BigDecimal;
import java.sql.Connection;

import static org.junit.Assert.*;

public class DbExecutorTest {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserTest.class);

    private Connection connection = new SessionManagerImp().getConnection();

}
