package ru.otus.hw12;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.hw12.dao.UserDao;
import ru.otus.hw12.dao.UserDaoImpl;
import ru.otus.hw12.helpers.FileSystemHelper;
import ru.otus.hw12.model.User;
import ru.otus.hw12.server.UsersWebServer;
import ru.otus.hw12.server.UsersWebServerImpl;
import ru.otus.hw12.services.*;

import static ru.otus.hw12.server.SecurityType.FILTER_BASED;


@Service
public class Demo {

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";

    private UserDao userDao;
    private final ApplicationContext context;

    public Demo(ApplicationContext context) {
        this.context = context;
        this.userDao = context.getBean(UserDaoImpl.class);
        createAdminUser();
    }


    private void createAdminUser() {
        User user = new User();
        user.setUserId(1);
        user.setUserName("Den");
        user.setUserLogin("admin");
        user.setUserPassword("123");
        userDao.saveUser(user);
    }

    void start() throws Exception {

        UserAuthService userAuthServiceForFilterBasedSecurity = new UserAuthServiceImpl(userDao);
        LoginService loginServiceForBasicSecurity = new InMemoryLoginServiceImpl(userDao);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
                FILTER_BASED,
                userAuthServiceForFilterBasedSecurity,
                loginServiceForBasicSecurity,
                userDao,
                gson,
                templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
