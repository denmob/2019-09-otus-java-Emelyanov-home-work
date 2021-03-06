package ru.otus.hw15.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "user")
public class User {

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }


    @Id
    private String id;
    private String name;
    private String login;
    private String password;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
