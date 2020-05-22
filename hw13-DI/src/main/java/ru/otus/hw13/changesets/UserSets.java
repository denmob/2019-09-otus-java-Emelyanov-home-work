package ru.otus.hw13.changesets;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.otus.hw13.domain.User;

@ChangeLog
public class UserSets {

  private static final String COLLECTION_NAME = "user";

  @ChangeSet(id = "withMongoDatabase", order = "001", author = "Mongock")
  public void changeSet1(MongoDatabase mongoDatabase) {
    mongoDatabase.getCollection(COLLECTION_NAME).insertOne(createMongoDocument(getClient("otus", "admin", "123")));
  }

  @ChangeSet(id = "withMongoDatabase", order = "002", author = "Mongock")
  public void changeSet2(MongoDatabase mongoDatabase) {
    mongoDatabase.getCollection(COLLECTION_NAME).insertOne(createMongoDocument(getClient("test", "admin123", "456")));
  }

  @ChangeSet(id = "withMongoDatabase", order = "003", author = "Mongock")
  public void changeSet3(MongoDatabase mongoDatabase) {
    mongoDatabase.getCollection(COLLECTION_NAME).insertOne(createMongoDocument(getClient("god", "qwerty", "789")));
  }

  private Document createMongoDocument(User user) {
    return new Document()
        .append("name", user.getName())
        .append("login", user.getLogin())
        .append("password", user.getPassword()
        );
  }

  private User getClient(String name, String login, String password) {
    return new User(name, login, password);
  }

}
