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

    @Override
    public boolean saveObject(Object object) {
        try {
            jdbcTemplateImp.create(object);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Object loadObject(long id, Class<?> clazz) {
        return this.jdbcTemplateImp.load(id,clazz);
    }

    @Override
    public boolean updateObject(Object object) {
        try {
            jdbcTemplateImp.update(object);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean createOrUpdate(Object object) {
        try {
            jdbcTemplateImp.createOrUpdate(object);
            return true;
        }catch (Exception e) {
            return false;
        }
    }


}
