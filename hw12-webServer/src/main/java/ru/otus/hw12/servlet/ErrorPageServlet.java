package ru.otus.hw12.servlet;

import ru.otus.hw12.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ErrorPageServlet extends HttpServlet {
    private static final String ERROR_PAGE_TEMPLATE = "error_page.ftl";
    private final TemplateProcessor templateProcessor;

    public ErrorPageServlet(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("errorMessage", 	request.getSession().getAttribute("errorMessage"));
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(ERROR_PAGE_TEMPLATE, paramsMap));
    }
}
