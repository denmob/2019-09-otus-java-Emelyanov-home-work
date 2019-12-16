package ru.otus.hw09.service;

import org.slf4j.LoggerFactory;
import ru.otus.hw09.jdbc.JDBCTemplateImp;
import ru.otus.hw09.jdbc.MyConnectionImp;

import java.util.Objects;

public class DbExecutorImp implements DbExecutor {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DbExecutorImp.class);

    private JDBCTemplateImp<Object> jdbcTemplateImp;

    public DbExecutorImp() {
        MyConnectionImp myConnectionImp1 =  new MyConnectionImp(false);
        this.jdbcTemplateImp = new JDBCTemplateImp<>(myConnectionImp1.getConnection());
    }

    public DbExecutorImp(MyConnectionImp myConnectionImp) {
        MyConnectionImp myConnectionImp1 = Objects.requireNonNullElseGet(myConnectionImp,
                () -> new MyConnectionImp(false));
        this.jdbcTemplateImp = new JDBCTemplateImp<>(myConnectionImp1.getConnection());
    }

    public boolean saveObject(Object object) {

        jdbcTemplateImp.create(object);
        return true;
    }

    @Override
    public boolean loadObject(Object object) {
        return false;
    }

    @Override
    public boolean updateObject(Object object) {
        return false;
    }


}
