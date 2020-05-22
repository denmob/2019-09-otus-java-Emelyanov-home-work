package ru.otus.hw13.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.hw13.domain.User;
import ru.otus.hw13.repostory.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


@Controller
public class UserController {

  private final UserRepository repository;

  private static final String MESSAGE_USER_FOUND = "User with this login already exists!";
  private static final String MESSAGE_USER_NOT_FOUND = "User login does not exist";
  private static final String MESSAGE_USER_DATA_INCORRECT = "User login or password incorrect";

  public UserController(UserRepository repository) {
    this.repository = repository;
  }

  @GetMapping({"/user/list"})
  public String userListView(Model model) {
    List<User> users = repository.getAllUsers();
    model.addAttribute("users", users);
    return "userList.html";
  }

  @GetMapping({"/"})
  public String indexView() {
    return "index.html";
  }

  @GetMapping("/user/create")
  public String userCreateView(Model model) {
    model.addAttribute("user", new User("Name", "login", "password"));
    return "userCreate.html";
  }

  @GetMapping("/error/page")
  public String errorPageView(@ModelAttribute("errorMessage") String errorMessage) {
    return "errorPage.html";
  }

  @GetMapping("/login")
  public String loginPageView(Model model) {
    model.addAttribute("user", new User("", "", ""));
    return "loginPage.html";
  }

  @PostMapping("/login")
  public RedirectView loginSubmit(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
    AtomicReference<RedirectView> redirectView = new AtomicReference<>();
    Optional<User> optionalUser = repository.findByUserLogin(user.getLogin());
    optionalUser.ifPresentOrElse(
        value -> {
          if (!value.getPassword().equals(user.getPassword())) {
            redirectAttributes.addFlashAttribute("errorMessage", MESSAGE_USER_DATA_INCORRECT).addFlashAttribute("isAuthenticated", false);
            redirectView.set(new RedirectView("/error/page", true));
          } else {
            redirectAttributes.addFlashAttribute("name", value.getName()).addFlashAttribute("isAuthenticated", true);
            redirectView.set(new RedirectView("/admin/page", true));
          }
        },
        () -> {
          redirectAttributes.addFlashAttribute("errorMessage", MESSAGE_USER_NOT_FOUND).addFlashAttribute("isAuthenticated", false);
          redirectView.set(new RedirectView("/error/page", true));
        }
    );
    return redirectView.get();
  }

  @GetMapping("/admin/page")
  public String adminPageView(@ModelAttribute("name") String name) {
    return "adminPage.html";
  }

  @PostMapping("/user/save")
  public RedirectView userSave(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
    AtomicReference<RedirectView> redirectView = new AtomicReference<>();
    Optional<User> optionalUser = repository.findByUserLogin(user.getLogin());
    optionalUser.ifPresentOrElse(
        value -> {
          redirectAttributes.addFlashAttribute("errorMessage", MESSAGE_USER_FOUND).addFlashAttribute("isAuthenticated", true);
          redirectView.set(new RedirectView("/error/page", true));
        },
        () -> {
          repository.saveUser(user);
          redirectView.set(new RedirectView("/user/list", true));
        }
    );
    return redirectView.get();
  }
}
