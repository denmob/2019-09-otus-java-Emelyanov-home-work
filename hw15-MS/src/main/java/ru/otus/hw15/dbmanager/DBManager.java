package ru.otus.hw15.dbmanager;


import com.mongodb.client.MongoDatabase;

public interface DBManager{
      MongoDatabase getMongoDatabase();
}
