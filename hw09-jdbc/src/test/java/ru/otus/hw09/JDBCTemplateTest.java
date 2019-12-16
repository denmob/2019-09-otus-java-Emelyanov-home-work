package ru.otus.hw09;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import ru.otus.hw09.jdbc.JDBCTemplateImp;
import ru.otus.hw09.jdbc.MyConnectionImp;
import ru.otus.hw09.model.User;
import ru.otus.hw09.service.ParseObjectOrClassImp;

import static org.junit.Assert.assertThrows;

public class JDBCTemplateTest {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JDBCTemplateTest.class);


    @Test
    public void JDBCTemplate1(){
        MyConnectionImp myConnectionImp = new MyConnectionImp();
        JDBCTemplateImp jdbcTemplateImp = new JDBCTemplateImp(myConnectionImp.getConnection());
        String sSql = "create table User(id bigint(20) NOT NULL auto_increment, name varchar(255),age int(3))";
        assertThrows(RuntimeException.class, () -> {
            jdbcTemplateImp.create(sSql);
            });
    }

    @Test
    public void JDBCTemplate2(){
        assertThrows(Exception.class, () -> {
                    MyConnectionImp myConnectionImp = new MyConnectionImp();
                    JDBCTemplateImp jdbcTemplateImp = new JDBCTemplateImp(myConnectionImp.getConnection());
                    jdbcTemplateImp.create(null);
                });
    }

    @Test
    public void JDBCTemplate3(){
        assertThrows(Exception.class, () -> {
            MyConnectionImp myConnectionImp = new MyConnectionImp();
            JDBCTemplateImp jdbcTemplateImp = new JDBCTemplateImp(myConnectionImp.getConnection());
            jdbcTemplateImp.create("select");
        });
    }

    @Test
    public void JDBCTemplate4(){
        assertThrows(Exception.class, () -> {
            MyConnectionImp myConnectionImp = new MyConnectionImp();
            JDBCTemplateImp<ParseObjectOrClassImp> jdbcTemplateImp = new JDBCTemplateImp<>(myConnectionImp.getConnection());
            ParseObjectOrClassImp parseObject = new ParseObjectOrClassImp(new User(1,"test",2));
            jdbcTemplateImp.create(parseObject);
        });
    }
}
