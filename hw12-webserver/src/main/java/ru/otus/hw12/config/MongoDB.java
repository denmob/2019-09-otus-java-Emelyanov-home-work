package ru.otus.hw12.config;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
@PropertySource("settings.yml")
public class MongoDB extends AbstractMongoConfiguration {

    @Value("${mongoHost}")
    private String host;

    @Value("${mongoPort}")
    private String port;

    @Value("${mongoDatabase}")
    private String database;

    @Override
    public MongoClient mongoClient() {
        return new MongoClient((new ServerAddress(host, Integer.parseInt(port))));
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }
}
