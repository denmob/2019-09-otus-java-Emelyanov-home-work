package ru.otus.hw15.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.hw15.domain.User;
import ru.otus.hw15.front.FrontendService;
import ru.otus.hw15.messagesystem.CommandType;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;



@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final FrontendService frontendService;

    private static final  String MESSAGE_USER_FOUND = "User with this login already exists!";
    private static final  String MESSAGE_USER_NOT_FOUND = "User login does not exist";
    private static final  String MESSAGE_USER_DATA_INCORRECT = "User login or password incorrect";

    public UserController(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @GetMapping({"/user/list"})
    public String userListView(Model model) {
        List<User> users = validateResultObjectWithUserList(getSynchronizedResult(CommandType.GET_AllUSERS,null));
        model.addAttribute("users", users);
        return "userList.html";
    }

    @GetMapping({"/"})
    public String indexView() {
        return "index.html";
    }

    @GetMapping("/user/create")
    public String userCreateView(Model model) {
        model.addAttribute("user",  new User("Name","login","password"));
        return "userCreate.html";
    }

    @GetMapping("/error/page")
    public String errorPageView(@ModelAttribute("errorMessage") String errorMessage ) {
        return "errorPage.html";
    }

    @GetMapping("/login")
    public String loginPageView(Model model ) {
        model.addAttribute("user", new User("","",""));
        return "loginPage.html";
    }

    @PostMapping("/login")
    public RedirectView loginSubmit(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        AtomicReference<RedirectView> redirectView = new AtomicReference<>();

        Optional<User> optionalUser = validateResultObjectWithUser(getSynchronizedResult(CommandType.GET_USER_WITH_LOGIN,user.getLogin()));
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
                () ->  {
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
        Optional<User> optionalUser = validateResultObjectWithUser(getSynchronizedResult(CommandType.GET_USER_WITH_LOGIN,user.getLogin()));
        optionalUser.ifPresentOrElse(
                value -> {
                    redirectAttributes.addFlashAttribute("errorMessage",  MESSAGE_USER_FOUND).addFlashAttribute("isAuthenticated", true);
                    redirectView.set(new RedirectView("/error/page", true));
                },
                () -> {
                    getSynchronizedResult(CommandType.SAVE_USER,user);
                    redirectView.set(new RedirectView("/user/list", true));
                }
        );
        return redirectView.get();
    }

    private Object getSynchronizedResult(CommandType commandType, Object param){
        var ref = new Object() {
            Object result = null;
        };
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread thread = new Thread(() -> {
            switch (commandType) {
                case SAVE_USER: {
                    frontendService.saveUser((User) param, newValue -> {
                        ref.result = newValue;
                        countDownLatch.countDown();
                    });
                    break;
                }
                case GET_USER_WITH_LOGIN:{
                    frontendService.getUserWithLogin((String) param, newValue -> {
                        ref.result = newValue;
                        countDownLatch.countDown();
                    });
                    break;
                }
                case GET_AllUSERS:{
                    frontendService.getAllUsers(newValue -> {
                        ref.result = newValue;
                        countDownLatch.countDown();
                    });
                    break;
                }
            }});
        thread.start();
        try {
            thread.join();
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return ref.result;
    }

    private Optional<User> validateResultObjectWithUser(Object result) {
        if (result instanceof Optional) {
            Optional optional = (Optional) result;
            if (optional.isPresent()) {
                result = optional.get();
                if (result instanceof User) {
                    User user = (User) result;
                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
    }

    private List<User> validateResultObjectWithUserList(Object result) {
        if (result instanceof List) {
            List users = (List) result;
            if (!users.isEmpty()) {
                if (users.get(0) instanceof User) {
                    return (List<User>) users;
                }
            }
        }
        return null;
    }

}
