package ru.otus.hw10.model;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    private static final Logger logger = LoggerFactory.getLogger(UserTest.class);

    @Test
    void emptyUserToString() {
        String expected = "User: id = 0, name = null, age = 0";
        User user = new User();
        logger.info(user.toString());
        assertEquals(expected, user.toString());
    }

    @Test
    void userToString() {
        String expected = "User: id = 0, name = Den, age = 31";
        User user = new User();
        user.setName("Den");
        user.setAge(31);
        logger.info(user.toString());
        assertEquals(expected, user.toString());
    }



}
