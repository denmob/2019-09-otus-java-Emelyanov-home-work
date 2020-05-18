package ru.otus.hw12.servlet;

import ru.otus.hw12.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminPageServlet extends HttpServlet {

  private static final String ADMIN_PAGE_TEMPLATE = "admin_page.ftl";
  private final TemplateProcessor templateProcessor;

  public AdminPageServlet(TemplateProcessor templateProcessor) {
    this.templateProcessor = templateProcessor;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Map<String, Object> paramsMap = new HashMap<>();
    paramsMap.put("name", request.getSession().getAttribute("name"));
    response.setContentType("text/html");
    response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, paramsMap));
  }
}
