package ru.otus.hw13.changesets;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw13.domain.User;

import java.time.LocalDateTime;

@ChangeLog
public class UserSets {

    private static final String COLLECTION_NAME = "user";

    @ChangeSet(id= "withMongoDatabase", order = "001", author = "Mongock")
    public void changeSet1(MongoDatabase mongoDatabase) {
        mongoDatabase.getCollection(COLLECTION_NAME).insertOne(createMongoDocument(getClient("otus", "admin", "123")));
    }

    @ChangeSet(id= "withMongoTemplate", order = "002", author = "Mongock")
    public void changeSet2(MongoTemplate template) {
        template.insert(getClient("MongoTemplate", "changeSet2", LocalDateTime.now().toString()), COLLECTION_NAME);
    }

    private Document createMongoDocument(User user) {
        return new Document()
                .append("name", user.getName())
                .append("login", user.getLogin())
                .append("password", user.getPassword()
                );
    }

    private User getClient(String name, String login, String password) {
        return new User(name, login,password);
    }

}