package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.netology.controller.PostController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ComponentScan("ru.netology")
public class MainServlet extends HttpServlet {
  private PostController controller;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();

      if (method.equals("GET") && path.equals("/api/posts")) {
        controller.all(resp);
        return;
      }
      if (method.equals("GET") && path.matches("/api/posts/\\d+")) {
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
        controller.getById(id, resp);
        return;
      }
      if (method.equals("POST") && path.equals("/api/posts")) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals("DELETE") && path.matches("/api/posts/\\d+")) {
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
        controller.removeById(id, resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
