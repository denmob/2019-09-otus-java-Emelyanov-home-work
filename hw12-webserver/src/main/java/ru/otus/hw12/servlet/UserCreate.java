package ru.otus.hw12.servlet;


import ru.otus.hw12.dao.UserDao;
import ru.otus.hw12.model.User;
import ru.otus.hw12.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserCreate extends HttpServlet {
	
	private static final String USER_NAME_PARAMETER = "name";
	private static final String USER_LOGIN_PARAMETER = "login";
	private static final String USER_PASSWORD_PARAMETER = "password";
	
	private static final String USER_CREATE_PAGE_TEMPLATE = "create_user.ftl";

	private static final String REDIRECT_URL = "/admin";


	private final UserDao userDao;

	public UserCreate(TemplateProcessor templateProcessor, UserDao userDao) {
		this.userDao = userDao;
		this.templateProcessor = templateProcessor;
	}

	private final TemplateProcessor templateProcessor;
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.getWriter().println(templateProcessor.getPage(USER_CREATE_PAGE_TEMPLATE, null));
    }
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User newUser = new User();
        newUser.setUserName(request.getParameter(USER_NAME_PARAMETER));
		newUser.setUserLogin(request.getParameter(USER_LOGIN_PARAMETER));
		newUser.setUserPassword(request.getParameter(USER_PASSWORD_PARAMETER));
		userDao.saveUser(newUser);
        response.sendRedirect(REDIRECT_URL);
    }
}
