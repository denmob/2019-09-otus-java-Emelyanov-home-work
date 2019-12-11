package ru.otus.hw09.jdbc;

import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;


public class MyConnectionImp implements  MyConnection{

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MyConnectionImp.class);

    private  String sURL = "jdbc:h2:mem:";


    private  boolean bAutoCommit = false;
    private  boolean bInitDriverManager;


    public MyConnectionImp(String sURL, boolean bAutoCommit) {
        this.sURL = sURL;
        this.bAutoCommit = bAutoCommit;
        this.bInitDriverManager = initConnection();
    }

    private  Connection connection;

    public MyConnectionImp() {
        this.bInitDriverManager = initConnection();
    }

    public Connection getConnection(){
        if (bInitDriverManager)
            return this.connection;
        else
            return null;
    }

    private boolean initConnection() {
        boolean result;
        try {
           this.connection = DriverManager.getConnection(this.sURL);
            this.connection.setAutoCommit(this.bAutoCommit);
            result = true;
        } catch (Exception e) {
           logger.error("initDriverManager Exception",e);
           throw new IllegalArgumentException(e.getMessage());
        }
        return result;
    }


}
