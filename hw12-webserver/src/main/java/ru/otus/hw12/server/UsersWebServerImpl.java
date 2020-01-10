package ru.otus.hw12.server;


import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.hw12.dao.UserDao;
import ru.otus.hw12.helpers.FileSystemHelper;
import ru.otus.hw12.services.TemplateProcessor;
import ru.otus.hw12.services.UserAuthService;
import ru.otus.hw12.servlet.*;

import java.util.stream.IntStream;


public class UsersWebServerImpl implements UsersWebServer {
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";
    private static final String ADMIN_PAGE_URL = "/admin";
    private static final String USERS_LIST_URL = "/admin/usersList";
    private static final String USER_CREATE_URL = "/admin/userCreate";

    private final int port;
    private final UserAuthService userAuthServiceForFilterBasedSecurity;
    private final UserDao userDao;
    private final TemplateProcessor templateProcessor;
    private final Server server;

    public UsersWebServerImpl(int port,
                              UserAuthService userAuthServiceForFilterBasedSecurity, UserDao userDao,
                              TemplateProcessor templateProcessor) {
        this.port = port;
        this.userAuthServiceForFilterBasedSecurity = userAuthServiceForFilterBasedSecurity;
        this.userDao = userDao;
        this.templateProcessor = templateProcessor;
        server = initContext();
    }

    @Override
    public void start() throws Exception {
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private Server initContext() {
        HandlerList handlers = new HandlerList();

        ResourceHandler resourceHandler = createResourceHandler();
        handlers.addHandler(resourceHandler);

        ServletContextHandler servletContextHandler = createServletContextHandler();
        handlers.addHandler(applySecurity(servletContextHandler));

        Server srv = new Server(port);
        srv.setHandler(handlers);
        return srv;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new UserList(templateProcessor, userDao)), USERS_LIST_URL);
        servletContextHandler.addServlet(new ServletHolder(new UserCreate(templateProcessor, userDao)), USER_CREATE_URL);
        servletContextHandler.addServlet(new ServletHolder(new AdminPage(templateProcessor)), ADMIN_PAGE_URL);
        return servletContextHandler;
    }

    private Handler applySecurity(ServletContextHandler servletContextHandler) {
        applyFilterBasedSecurity(servletContextHandler, "/admin");
        return servletContextHandler;
    }

    private ServletContextHandler applyFilterBasedSecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, userAuthServiceForFilterBasedSecurity)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        IntStream.range(0, paths.length)
                .forEachOrdered(i -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), paths[i], null));
        return servletContextHandler;
    }

}
