package ru.otus.hw12;

import org.springframework.context.ApplicationContext;
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
    private UserDao userDao;

    Demo(ApplicationContext context) {
        this.userDao = context.getBean(UserDaoImpl.class);
        createAdminUser();
    }

    private void createAdminUser() {
        User user = new User();
        user.setUserName("Otus");
        user.setUserLogin("admin");
        user.setUserPassword("123");
        userDao.saveUser(user);
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
