package ru.otus.hw15;

import com.mongodb.client.MongoDatabase;
import ru.otus.hw15.dao.UserDao;
import ru.otus.hw15.dao.UserDaoImpl;
import ru.otus.hw15.dbmanager.DBManagerImpl;
import ru.otus.hw15.model.User;
import ru.otus.hw15.server.UsersWebServer;
import ru.otus.hw15.server.UsersWebServerImpl;
import ru.otus.hw15.services.TemplateProcessor;
import ru.otus.hw15.services.TemplateProcessorImpl;
import ru.otus.hw15.services.UserAuthService;
import ru.otus.hw15.services.UserAuthServiceImpl;

import java.io.IOException;


class Demo {

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    private UsersWebServer usersWebServer;
    private final UserDao userDao;

    Demo() throws IOException {
        MongoDatabase mongoDatabase = new DBManagerImpl().getMongoDatabase();
        userDao = new UserDaoImpl(mongoDatabase.getCollection("user",User.class));

        userDao.saveUser(createAdminUser());
        createWebServer();
    }


    private User createAdminUser() {
        User user = new User();
        user.setName("Otus");
        user.setLogin("admin");
        user.setPassword("123");
       return user;
    }

    private void createWebServer() throws IOException {
        UserAuthService userAuthServiceForFilterBasedSecurity = new UserAuthServiceImpl(userDao);
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
         usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
                userAuthServiceForFilterBasedSecurity,
                 userDao,
                templateProcessor);
    }

    void start() throws Exception {
        usersWebServer.start();
    }
}
