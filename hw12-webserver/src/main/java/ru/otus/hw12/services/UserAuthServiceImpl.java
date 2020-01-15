package ru.otus.hw12.services;


public class UserAuthServiceImpl implements UserAuthService {

    private final ORMService ormService;

    public UserAuthServiceImpl(ORMService ormService) {
        this.ormService = ormService;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return ormService.findByUserLogin(login)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

}
