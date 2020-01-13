package ru.otus.hw12;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw12.dao.UserDao;
import ru.otus.hw12.dao.UserDaoImpl;
import ru.otus.hw12.model.User;
import ru.otus.hw12.server.UsersWebServer;
import ru.otus.hw12.server.UsersWebServerImpl;
import ru.otus.hw12.services.TemplateProcessor;
import ru.otus.hw12.services.TemplateProcessorImpl;
import ru.otus.hw12.services.UserAuthService;
import ru.otus.hw12.services.UserAuthServiceImpl;


class Demo {

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String MONGO_DB_HOST = "localhost";
    private static final int MONGO_DB_PORT = 27017;
    private final UserDao userDao;
    private MongoCollection<User> usersCollection;

    Demo() {
        this.usersCollection = initMongoDB();
        this.userDao = new UserDaoImpl(usersCollection);
        this.userDao.saveUser(createAdminUser());
    }

    private MongoCollection<User>  initMongoDB() {
        MongoDatabase database;
        try (MongoClient mongoClient = new MongoClient(MONGO_DB_HOST,MONGO_DB_PORT)) {
            database = mongoClient.getDatabase("hw12");
        }
        return  database.getCollection("user",User.class);
    }

    private User createAdminUser() {
        User user = new User();
        user.setID(1L);
        user.setName("Otus");
        user.setLogin("admin");
        user.setPassword("123");
       return user;
    }

    void start() throws Exception {
        UserAuthService userAuthServiceForFilterBasedSecurity = new UserAuthServiceImpl(userDao);
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
                userAuthServiceForFilterBasedSecurity,
                userDao,
                templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
