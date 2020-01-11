package ru.otus.hw12.servlet;


import ru.otus.hw12.dao.UserDao;
import ru.otus.hw12.model.User;
import ru.otus.hw12.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserCreate extends HttpServlet {
	
	private static final String USER_NAME_PARAMETER = "name";
	private static final String USER_LOGIN_PARAMETER = "login";

	private static final String USER_CREATE_PAGE_TEMPLATE = "create_user.ftl";

	private static final String REDIRECT_ADMIN_PAGE = "/admin";

	private static final String TEMPLATE_ERROR_PAGE = "error_page.ftl";
	private static final String TEMPLATE_ERROR_MESSAGE = "errorMessage";
	private static final String ERROR_MESSAGE_USER_FOUND = "Пользователь с таким логином уже существует!";



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

		if (userDao.findByUserLogin(request.getParameter(USER_LOGIN_PARAMETER)).isPresent()) {
			Map<String, Object> paramsMap = new HashMap<>();
			paramsMap.put(TEMPLATE_ERROR_MESSAGE, ERROR_MESSAGE_USER_FOUND);
			response.setContentType("text/html");
			response.getWriter().println(templateProcessor.getPage(TEMPLATE_ERROR_PAGE, paramsMap));
		} else {
			User newUser = new User();
			newUser.setUserName(request.getParameter(USER_NAME_PARAMETER));
			newUser.setUserLogin(request.getParameter(USER_LOGIN_PARAMETER));
			newUser.setUserPassword(request.getParameter("password"));
			userDao.saveUser(newUser);
			response.sendRedirect(REDIRECT_ADMIN_PAGE);
		}
    }
}
