package ru.otus.hw13.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;


@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    private static final String MONGO_BD_HOST = "127.0.0.1";
    private static final String MONGO_BD_DB_NAME = "hw13";

    @Override
    public String getDatabaseName() {
        return MONGO_BD_DB_NAME;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(MONGO_BD_HOST);
    }
}
