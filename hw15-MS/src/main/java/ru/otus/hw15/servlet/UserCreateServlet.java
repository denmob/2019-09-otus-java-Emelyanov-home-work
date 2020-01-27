package ru.otus.hw15.servlet;


import ru.otus.hw15.dao.UserDao;
import ru.otus.hw15.model.User;
import ru.otus.hw15.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserCreateServlet extends HttpServlet {
	
	private static final String USER_NAME_PARAMETER = "userName";
	private static final String USER_LOGIN_PARAMETER = "userLogin";
	private static final String USER_PW_PARAMETER = "userPassword";

	private static final String USER_CREATE_PAGE_TEMPLATE = "create_user.ftl";

	private static final String REDIRECT_ADMIN_PAGE = "/admin";
	private static final String REDIRECT_ERROR_PAGE = "/admin/errorPage";

	private final UserDao userDao;

	public UserCreateServlet(TemplateProcessor templateProcessor, UserDao userDao) {
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
			HttpSession session = request.getSession();
			session.setAttribute("errorMessage","Пользователь с таким логином уже существует!");
			response.sendRedirect(REDIRECT_ERROR_PAGE);
		} else {
			User newUser = new User();
			newUser.setName(request.getParameter(USER_NAME_PARAMETER));
			newUser.setLogin(request.getParameter(USER_LOGIN_PARAMETER));
			newUser.setPassword(request.getParameter(USER_PW_PARAMETER));
			userDao.saveUser(newUser);
			response.sendRedirect(REDIRECT_ADMIN_PAGE);
		}
    }
}
