package ru.otus.hw09;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import ru.otus.hw09.model.Account;
import ru.otus.hw09.model.User;
import ru.otus.hw09.service.ParseObjectOrClassImp;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ParseObjectOrClassTest {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ParseObjectOrClassTest.class);


    @Test
    public void createInsertCommandFromObject(){
        User user = new User(1,"Satoshi Nakamoto",59);
        ParseObjectOrClassImp parseObject = new ParseObjectOrClassImp(user);
        String sExcept = "insert into User (id, name, age) values (?, ?, ?)";
        String sActual = parseObject.getInsertCommand();
        logger.info(sActual);
        assertEquals(sExcept,sActual);
    }

    @Test
    public void createUpdateCommandFromObject(){
        User user = new User(1,"Satoshi Nakamoto",59);
        ParseObjectOrClassImp parseObject = new ParseObjectOrClassImp(user);
        String sExcept = "update User set name = ?, age = ? where id = ?";
        String sActual = parseObject.getUpdateCommand();
        logger.info(sActual);
        assertEquals(sExcept,sActual);
    }

    @Test
    public void createSelectCommandFromObject(){
        User user = new User(1,"Satoshi Nakamoto",59);
        ParseObjectOrClassImp parseObject = new ParseObjectOrClassImp(user);
        String sExcept = "select id, name, age from User where id = ?";
        String sActual = parseObject.getSelectCommand();
        logger.info(sActual);
        assertEquals(sExcept,sActual);
    }

    @Test
    public void getInsertValues() {
        User user = new User(1,"Satoshi Nakamoto",59);
        ParseObjectOrClassImp parseObject = new ParseObjectOrClassImp(user);
        String sExcept = "{1=1, 2=Satoshi Nakamoto, 3=59}";
        String sActual = String.valueOf(parseObject.getInsertValues());
        logger.info(sActual);
        assertEquals(sExcept,sActual);
    }

    @Test
    public void getUpdateValues() {
        User user = new User(1,"Satoshi Nakamoto",59);
        ParseObjectOrClassImp parseObject = new ParseObjectOrClassImp(user);
        String sExcept = "{1=Satoshi Nakamoto, 2=59}";
        String sActual = String.valueOf(parseObject.getUpdateValues());
        logger.info(sActual);
        assertEquals(sExcept,sActual);
    }

    @Test
    public void getCreateTableCommandFromObjectUser1() {
        User user = new User(2,"Satoshi Nakamoto",59);
        ParseObjectOrClassImp parseObject = new ParseObjectOrClassImp(user);
        String sExcept = "create table IF NOT EXISTS User (id bigint (20) NOT NULL auto_increment ,name varchar(255) ,age int (3) )";
        String sActual = parseObject.getCreateCommand();
        logger.info(sActual);
        assertEquals(sExcept,sActual);
    }

    @Test
    public void getCreateTableCommandFromObjectAccount1() {
        Account account = new Account(2,"Satoshi Nakamoto", BigDecimal.valueOf(66));
        ParseObjectOrClassImp parseObject = new ParseObjectOrClassImp(account);
        String sExcept = "create table IF NOT EXISTS Account (no bigint (20) NOT NULL auto_increment ,type varchar(255) ,rest number )";
        String sActual = parseObject.getCreateCommand();
        logger.info(sActual);
        assertEquals(sExcept,sActual);
    }

    @Test
    public void createInsertCommandFromClass(){

        ParseObjectOrClassImp parseObject = new ParseObjectOrClassImp(User.class);
        String sExcept = "insert into User (id, name, age) values (?, ?, ?)";
        String sActual = parseObject.getInsertCommand();
        logger.info(sActual);
        assertEquals(sExcept,sActual);
    }

    @Test
    public void createUpdateCommandFromClass(){
        ParseObjectOrClassImp parseObject = new ParseObjectOrClassImp(User.class);
        String sExcept = "update User set name = ?, age = ? where id = ?";
        String sActual = parseObject.getUpdateCommand();
        logger.info(sActual);
        assertEquals(sExcept,sActual);
    }

    @Test
    public void createSelectCommandFromClass(){
        ParseObjectOrClassImp parseObject = new ParseObjectOrClassImp(User.class);
        String sExcept = "select id, name, age from User where id = ?";
        String sActual = parseObject.getSelectCommand();
        logger.info(sActual);
        assertEquals(sExcept,sActual);
    }


}
