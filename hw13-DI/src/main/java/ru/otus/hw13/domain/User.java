package ru.otus.hw13.domain;


import org.bson.types.ObjectId;

import java.util.Objects;

public class User {

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public User() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    private ObjectId id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String name;
    private String login;
    private String password;

    @Override
    public String toString() {
        return "User: id = " + id
                + ", name = " + name
                + ", login = " + login
                + ", password = " + password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return  id.equals(that.id) &&
                name.equals(that.name) &&
                name.equals(that.login) &&
                password == that.password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, password);
    }



}
