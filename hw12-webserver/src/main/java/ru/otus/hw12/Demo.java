package ru.otus.hw12;

import ru.otus.hw12.dbmanager.DBManager;
import ru.otus.hw12.dbmanager.DBManagerImpl;
import ru.otus.hw12.model.User;
import ru.otus.hw12.server.UsersWebServer;
import ru.otus.hw12.server.UsersWebServerImpl;
import ru.otus.hw12.services.*;


class Demo {

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private final  ORMService ormService;
    private final DBManager dbManager = new DBManagerImpl();

    Demo() {
        ormService = new ORMServiceImpl(dbManager);
        ormService.saveUser(createAdminUser());
    }


    private User createAdminUser() {
        User user = new User();
        user.setName("Otus");
        user.setLogin("admin");
        user.setPassword("123");
       return user;
    }

    void start() throws Exception {
        UserAuthService userAuthServiceForFilterBasedSecurity = new UserAuthServiceImpl(ormService);
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
                userAuthServiceForFilterBasedSecurity,
                ormService,
                templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
