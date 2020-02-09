package ru.otus.hw16;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class SocketClientDBService {

    public static void main(String[] args) {
        SpringApplication.run(SocketClientDBService.class, args);
    }

}