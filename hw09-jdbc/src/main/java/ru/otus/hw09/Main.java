package ru.otus.hw09;


import org.slf4j.LoggerFactory;
import ru.otus.hw09.jdbc.JDBCTemplateImp;
import ru.otus.hw09.jdbc.MyConnectionImp;

public class Main {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)  {

        logger.info(" hw09 {}", (Object[]) args);

        MyConnectionImp myConnectionImp = new MyConnectionImp();
        JDBCTemplateImp jdbcTemplateImp = new JDBCTemplateImp(myConnectionImp.getConnection());
        String sSql = "1";// "create table User(id bigint(20) NOT NULL auto_increment, name varchar(255),age int(3))";
        jdbcTemplateImp.create(sSql);


//
    }
}
