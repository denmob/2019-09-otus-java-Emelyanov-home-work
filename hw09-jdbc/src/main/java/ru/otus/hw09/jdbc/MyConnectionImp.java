package ru.otus.hw09.jdbc;

import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;


public class MyConnectionImp implements MyConnection {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MyConnectionImp.class);

    private  String sURL = "jdbc:h2:mem:";
    private  boolean bAutoCommit = false;
    private  boolean bInitDriverManager;


    public MyConnectionImp(String sURL, boolean bAutoCommit) {
        if ((sURL == null) || sURL.isEmpty()) throw new IllegalArgumentException("sUrl is null or empty!");
        this.sURL = sURL;
        this.bAutoCommit = bAutoCommit;
        this.bInitDriverManager = initConnection();
        if (!bInitDriverManager) throw new IllegalArgumentException("MyConnectionImp not be created with sUrl="+sURL +". Details in log.");
    }

    private  Connection connection;

    public MyConnectionImp(boolean bAutoCommit) {
        this.bAutoCommit = bAutoCommit;
        this.bInitDriverManager = initConnection();
    }

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
        try {
           this.connection = DriverManager.getConnection(this.sURL);
            this.connection.setAutoCommit(this.bAutoCommit);
            return true;
        } catch (Exception e) {
           logger.error("initDriverManager Exception",e);
           return false;
        }
    }

}
