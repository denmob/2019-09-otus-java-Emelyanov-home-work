package ru.otus.hw12.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public class User {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    private String id;

    @Field("user_id")
    private long userId;
    @Field("user_name")
    private String userName;
    @Field("user_login")
    private String userLogin;
    @Field("user_password")
    private String userPassword;

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", UserId=" + userId +
                ", userName='" + userName + '\'' +
                ", userLogin=" + userLogin +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
