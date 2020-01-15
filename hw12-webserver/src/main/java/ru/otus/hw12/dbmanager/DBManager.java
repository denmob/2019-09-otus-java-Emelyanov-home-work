package ru.otus.hw12.dbmanager;


import com.mongodb.client.MongoDatabase;

public interface DBManager{
      MongoDatabase getMongoDatabase();
}
