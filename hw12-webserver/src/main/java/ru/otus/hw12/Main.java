package ru.otus.hw12;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class Main  {

    public static void main(String[] args) throws Exception {
        ApplicationContext context =  SpringApplication.run(Main.class, args);
        Demo demo = new Demo(context);
        demo.start();
    }

}
