package ru.otus.hw09;


import org.slf4j.LoggerFactory;
import ru.otus.hw09.model.Account;
import ru.otus.hw09.model.User;
import ru.otus.hw09.service.DbExecutorImp;

import java.math.BigDecimal;

public class Main {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)  {

        User user1 = new User(1,"Bill",2);
        User user2 = new User(2,"Max",20);
        Account account1 = new Account(1,"type1", BigDecimal.valueOf(77));
        Account account2 = new Account(2,"type2", BigDecimal.valueOf(55));

        DbExecutorImp dbExecutorImp = new DbExecutorImp();
        dbExecutorImp.saveObject(user1);
        dbExecutorImp.saveObject(user2);
        dbExecutorImp.saveObject(account1);
        dbExecutorImp.saveObject(account2);

    }
}
