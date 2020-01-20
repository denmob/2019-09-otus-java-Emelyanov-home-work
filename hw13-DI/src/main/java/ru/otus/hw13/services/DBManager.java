package ru.otus.hw13.services;


import com.mongodb.client.MongoDatabase;

public interface DBManager{
      MongoDatabase getMongoDatabase();
}
