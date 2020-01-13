package ru.otus.hw12.model;


import com.mongodb.BasicDBObject;

import java.util.Objects;

public class User extends BasicDBObject {

    private static final long serialVersionUID = 2105061907470199595L;

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    public static final String COLLECTION_NAME = "user";

    public Long getId() {
        return getLong(ID);
    }

    public  String getName() {
        return getString(NAME);
    }

    public String getLogin() {
        return  getString(LOGIN);
    }

    public String getPassword() {
        return  getString(PASSWORD);
    }

    public void setID(long id) {
         put(ID,id);
    }

    public void setName(String name) {
         put(NAME,name);
    }

    public void setLogin(String login) {
        put(LOGIN,login);
    }

    public void setPassword(String password) {
        put(PASSWORD,password);
    }


    @Override
    public String toString() {
        return "User{" +
                ", id=" + ID +
                ", name='" + NAME +
                ", login=" + LOGIN +
                ", password='" + COLLECTION_NAME +
                '}';
    }


    @Override
    public int hashCode() {
        return Objects.hash(ID, NAME,LOGIN,COLLECTION_NAME);
    }
}
