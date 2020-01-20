package ru.otus.hw12.servlet;


import ru.otus.hw12.dao.UserDao;
import ru.otus.hw12.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class UserListServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "user_list.ftl";
    private static final String TEMPLATE_ATTR_USERS = "users";

    private final UserDao userDao;
    private final TemplateProcessor templateProcessor;

    public UserListServlet(TemplateProcessor templateProcessor, UserDao userDao) {
        this.templateProcessor = templateProcessor;
        this.userDao = userDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_ATTR_USERS, userDao.getAllUsers());
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

}
