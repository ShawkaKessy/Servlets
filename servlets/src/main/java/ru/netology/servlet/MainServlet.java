package ru.netology.servlet;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import ru.netology.controller.PostController;
import ru.netology.service.PostService;
import ru.netology.repository.PostRepository;
import ru.netology.config.AppConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {

  @Override
  public void init() throws ServletException {
    // Инициализация контекста Spring
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.register(AppConfig.class);
    context.refresh();

    // Получаем бины из Spring контекста
    PostRepository postRepository = context.getBean(PostRepository.class);
    PostService postService = new PostService(postRepository);
    PostController controller = new PostController(postService);

    // Устанавливаем контроллер
    getServletContext().setAttribute("controller", controller);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {
    PostController controller = (PostController) getServletContext().getAttribute("controller");
    // Логика обработки запроса
  }
}

