package ru.otus.hw09;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import ru.otus.hw09.jdbc.JDBCTemplate;
import ru.otus.hw09.model.Account;
import ru.otus.hw09.service.DbExecutor;
import ru.otus.hw09.service.DbExecutorImp;
import ru.otus.hw09.sessionmanager.SessionManager;
import ru.otus.hw09.sessionmanager.SessionManagerImp;
import ru.otus.hw09.jdbc.JDBCTemplateImp;
import ru.otus.hw09.model.User;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;


public class JDBCTemplateTest {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JDBCTemplateTest.class);

    private SessionManager sessionManager = new SessionManagerImp();



    @Test
    public void JDBCTemplate1(){
        DbExecutor<Account> dbExecutor = new DbExecutorImp<>();
        JDBCTemplate<Account> jdbcTemplate = new JDBCTemplateImp<>(sessionManager,dbExecutor);

        jdbcTemplate.createTable(Account.class);

        Account account1 = new Account("type1", BigDecimal.valueOf(77));
        account1 = (Account) jdbcTemplate.create(account1);
        Long  aLong =account1.getNo();

        Account account2  = jdbcTemplate.load(aLong, Account.class);

        account2.setType("type33");
        account2.setRest(BigDecimal.valueOf(99));

        jdbcTemplate.update(account2);

        Account account3  = jdbcTemplate.load(account1.getNo(), Account.class);

        assertEquals(account2,account3);
    }

    @Test
    public void JDBCTemplate2(){
        DbExecutor<User> dbExecutor = new DbExecutorImp<>();
        JDBCTemplate<User> userJDBCTemplate = new JDBCTemplateImp<>(sessionManager,dbExecutor);

        userJDBCTemplate.createTable(User.class);

        User user1 = new User( "Bill", 2);
        userJDBCTemplate.create(user1);

        User user2 =  userJDBCTemplate.load(user1.getId(), User.class);

        user2.setName("Will");
        user2.setAge(77);

        userJDBCTemplate.update(user2);

        User user3 = userJDBCTemplate.load(user1.getId(), User.class);
        assertEquals(user2,user3);
    }

    @Test
    public void JDBCTemplate3(){
        DbExecutor<Account> dbExecutor = new DbExecutorImp<>();
        JDBCTemplate<Account>  accountJDBCTemplate = new JDBCTemplateImp<>(sessionManager,dbExecutor);

        accountJDBCTemplate.createTable(Account.class);

        Account account1 = new Account("type1", BigDecimal.valueOf(77));

        Account account2 = (Account) accountJDBCTemplate.createOrUpdate(account1);

        assertEquals(account1,account2);

        account2.setType("type89");

        accountJDBCTemplate.createOrUpdate(account2);

        Account account3  = accountJDBCTemplate.load(account2.getNo(), Account.class);

        assertEquals(account2,account3);
    }

}
