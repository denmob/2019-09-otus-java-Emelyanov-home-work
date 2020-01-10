package ru.otus.hw12.servlet;

import ru.otus.hw12.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminPage extends HttpServlet {

	private static final String ADMIN_PAGE_TEMPLATE = "admin_page.ftl";

	private final TemplateProcessor templateProcessor;

	public AdminPage(TemplateProcessor templateProcessor) {
		this.templateProcessor = templateProcessor;
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, null));
    }
}
