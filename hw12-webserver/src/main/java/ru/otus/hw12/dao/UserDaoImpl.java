package ru.otus.hw12.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw12.model.User;
import ru.otus.hw12.repository.UserRepository;
import ru.otus.hw12.services.SequenceGeneratorService;

import java.util.List;
import java.util.Optional;


@Service
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoOperations mongoOperations;


    @Override
    public Optional<User> findByUserId(Long userId) {
       return Optional.ofNullable(userRepository.findByUserId(userId));
    }

    @Override
    public Optional<User> findByUserLogin(String userLogin) {
        return Optional.ofNullable(userRepository.findByUserLogin(userLogin));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (checkUserData(user)) {
            prepareUserToSave(user);
            userRepository.save(user);
        }
    }

    private boolean checkUserData(User user) {
        Optional<User> foundUser = findByUserLogin(user.getUserLogin());
        return foundUser.isEmpty();
    }

    private void prepareUserToSave(User user) {
        if (user.getUserId() == 0) {
            user.setUserId(SequenceGeneratorService.getNextSequence(mongoOperations, "customSequences"));
        }
    }


}
