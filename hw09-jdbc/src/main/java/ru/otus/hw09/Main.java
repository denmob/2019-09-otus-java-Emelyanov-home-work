package ru.otus.hw09;


import org.slf4j.LoggerFactory;
import ru.otus.hw09.jdbc.JDBCTemplate;
import ru.otus.hw09.jdbc.JDBCTemplateImp;
import ru.otus.hw09.model.Account;
import ru.otus.hw09.model.User;
import ru.otus.hw09.service.DbExecutor;
import ru.otus.hw09.service.DbExecutorImp;
import ru.otus.hw09.sessionmanager.SessionManager;
import ru.otus.hw09.sessionmanager.SessionManagerImp;

import java.math.BigDecimal;

public class Main {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)  {

        SessionManager sessionManager = new SessionManagerImp();
        DbExecutor dbExecutor = new DbExecutorImp();

//-------------------------------------
        JDBCTemplate<Account> jdbcTemplate = new JDBCTemplateImp<>(sessionManager,dbExecutor);

        jdbcTemplate.createTable(Account.class);

        Account account1 = new Account(1,"type1", BigDecimal.valueOf(77));
        jdbcTemplate.create(account1);

        Account account2  = jdbcTemplate.load(account1.getNo(), Account.class);

        account2.setType("type33");
        account2.setRest(BigDecimal.valueOf(99));

        jdbcTemplate.update(account2);

        Account account3  = jdbcTemplate.load(account1.getNo(), Account.class);
        logger.debug("saveObject {}",account1);
        logger.debug("loadObject {}",account2);
        logger.debug("updateObject {}",account3);

//-------------------------------------

        JDBCTemplate<User> userJDBCTemplate = new JDBCTemplateImp<>(sessionManager,dbExecutor);

        userJDBCTemplate.createTable(User.class);

        User user1 = new User(1, "Bill", 2);
        userJDBCTemplate.create(user1);

        User user2 =  userJDBCTemplate.load(user1.getId(), User.class);

        user2.setName("Will");
        user2.setAge(77);

        userJDBCTemplate.update(user2);

        User user3 = userJDBCTemplate.load(user1.getId(), User.class);
        logger.debug("saveObject {}",user1);
        logger.debug("loadObject {}",user2);
        logger.debug("updateObject {}",user3);

//-------------------------------------
        JDBCTemplate<Account>  accountJDBCTemplate = new JDBCTemplateImp<>(sessionManager,dbExecutor);

        jdbcTemplate.createTable(Account.class);

        Account account4 = new Account(1,"type1", BigDecimal.valueOf(77));
        accountJDBCTemplate.createOrUpdate(account4);
        Account account5 = new Account(1,"type89", BigDecimal.valueOf(12));
        accountJDBCTemplate.createOrUpdate(account5);
        Account account6  = accountJDBCTemplate.load(account1.getNo(), Account.class);

        logger.debug("createOrUpdate first {}",account4);
        logger.debug("createOrUpdate second {}",account5);
        logger.debug("createOrUpdate result {}",account6);

    }
}
