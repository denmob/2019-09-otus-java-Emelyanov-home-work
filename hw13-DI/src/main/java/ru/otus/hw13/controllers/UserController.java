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


@Controller
public class UserController {

    private final UserRepository repository;

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

    private User createAdminUser() {
        User user = new User();
        user.setName("Otus");
        user.setLogin("admin");
        user.setPassword("123");
        return user;
    }

    @GetMapping("/user/create")
    public String userCreateView(Model model) {
        model.addAttribute("user", createAdminUser());
        return "userCreate.html";
    }

    @GetMapping("/error/page")
    public String errorPageView(@ModelAttribute("errorMessage") String errorMessage ) {
        return "errorPage.html";
    }

    @PostMapping("/login")
    public RedirectView loginSubmit(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser =repository.findByUserLogin(user.getLogin());

        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Пользователя с таким логином не существует!");
            return new RedirectView("/error/page", true);
        } else
        if  (!optionalUser.get().getPassword().equals(user.getPassword()))  {
            redirectAttributes.addFlashAttribute("errorMessage", "Пользователя с таким логином и паролем не существует!");
            return new RedirectView("/error/page", true);
        } else {
            repository.saveUser(user);
            return new RedirectView("/admin/page", true);
        }
    }

    @GetMapping("/admin/page")
    public String фвьштPageView(@ModelAttribute("name") String errorMessage ) {
        return "errorPage.html";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        if (repository.findByUserLogin(user.getLogin()).isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage",  "Пользователь с таким логином уже существует!");
            return new RedirectView("/error/page", true);
        } else {
            repository.saveUser(user);
            return new RedirectView("/user/list", true);
        }
    }



}
