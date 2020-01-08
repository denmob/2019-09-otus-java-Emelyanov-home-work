package ru.otus.hw12;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw12.dao.UserDao;
import ru.otus.hw12.helpers.FileSystemHelper;
import ru.otus.hw12.server.UsersWebServer;
import ru.otus.hw12.server.UsersWebServerImpl;
import ru.otus.hw12.services.TemplateProcessor;
import ru.otus.hw12.services.TemplateProcessorImpl;
import ru.otus.hw12.services.UserAuthService;
import ru.otus.hw12.services.UserAuthServiceImpl;

import static ru.otus.hw12.server.SecurityType.FILTER_BASED;

@Configuration
@SpringBootApplication
public class Main {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";

    @Autowired
    private static UserDao userDao;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
        UserAuthService userAuthServiceForFilterBasedSecurity = new UserAuthServiceImpl(userDao);
        LoginService loginServiceForBasicSecurity = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);
        //LoginService loginServiceForBasicSecurity = new InMemoryLoginServiceImpl(userDao);

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
