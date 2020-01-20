package ru.otus.hw13.repostory;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.springframework.stereotype.Repository;
import ru.otus.hw13.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final MongoCollection<User> usersCollection;

    public UserRepositoryImpl(MongoCollection<User> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @Override
    public Optional<User> findByUserLogin(String userLogin) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("login", userLogin);
        List<User> userList = convertFindIterableToList(usersCollection.find(whereQuery));
        if (userList.size()>1) throw new IllegalStateException(String.format("Found %s users with login %s",userList.size(),userLogin));
        if (userList.isEmpty()) return Optional.empty();
        return Optional.ofNullable(userList.get(0));
    }

    private List<User> convertFindIterableToList(FindIterable<User> users){
        List<User> userList = new ArrayList<>();
        for(User user: users) userList.add(user);
        return userList;
    }

    @Override
    public List<User> getAllUsers() {
        FindIterable<User> users = usersCollection.find();
        return convertFindIterableToList(users);
    }

    @Override
    public void saveUser(User user) {
        if (checkUserData(user)) {
            usersCollection.insertOne(user);
        }
    }

    private boolean checkUserData(User user) {
        Optional<User> foundUser = findByUserLogin(user.getLogin());
        return foundUser.isEmpty();
    }

}
