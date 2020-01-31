package ru.otus.hw15.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.hw15.domain.User;
import ru.otus.hw15.front.FrontEndSynchronousService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class UserController {

    private static final  String MESSAGE_USER_FOUND = "User with this login already exists!";
    private static final  String MESSAGE_USER_NOT_FOUND = "User login does not exist";
    private final FrontEndSynchronousService frontEndSynchronousService;

    public UserController(FrontEndSynchronousService frontEndSynchronousService) {
        this.frontEndSynchronousService = frontEndSynchronousService;
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {
        String userLogin = (String) request.getSession().getAttribute("userLogin");

        if (userLogin == null || userLogin.isEmpty()) {
            return "redirect:/login";
        }
        model.addAttribute("userLogin", userLogin);

        return "redirect:/admin/page";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String showLoginPage() {
        return "loginPage";
    }

    @RequestMapping(path = "/admin/page", method = RequestMethod.GET)
    public String showAdminPage() {
        return "adminPage";
    }

    @RequestMapping(path = "/chat/websocket", method = RequestMethod.GET)
    public String showChatPage(HttpServletRequest request, Model model) {
        String userLogin = (String) request.getSession().getAttribute("userLogin");
        model.addAttribute("userLogin", userLogin);
        return "chatPage";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, @RequestParam(defaultValue = "") String userLogin) {
        Optional<User> optionalUser =frontEndSynchronousService.getUserWithLogin(userLogin);
        if (optionalUser.isPresent()) {
            request.getSession().setAttribute("userLogin", userLogin);
            return "redirect:/";
        } else {
            request.setAttribute("errorMessage",  MESSAGE_USER_NOT_FOUND);
            request.setAttribute("isAuthenticated",  false);
            return "errorPage";
        }
    }

    @RequestMapping(path = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession(true).invalidate();

        return "redirect:/login";
    }

    @GetMapping({"/user/list"})
    public String userListView(Model model) {
        List<User> users = frontEndSynchronousService.getAllUsers();
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/user/create")
    public String userCreateView(Model model) {
        model.addAttribute("user",  new User("Name","login","password"));
        return "userCreate";
    }

    @GetMapping("/error/page")
    public String errorPageView(@ModelAttribute("errorMessage") String errorMessage ) {
        return "errorPage";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        AtomicReference<RedirectView> redirectView = new AtomicReference<>();
        Optional<User> optionalUser = frontEndSynchronousService.getUserWithLogin(user.getLogin());
        optionalUser.ifPresentOrElse(
                value -> {
                    redirectAttributes.addFlashAttribute("errorMessage",  MESSAGE_USER_FOUND).addFlashAttribute("isAuthenticated", true);
                    redirectView.set(new RedirectView("/error/page", true));
                },
                () -> {
                    frontEndSynchronousService.saveUser(user);
                    redirectView.set(new RedirectView("/user/list", true));
                }
        );
        return redirectView.get();
    }
}