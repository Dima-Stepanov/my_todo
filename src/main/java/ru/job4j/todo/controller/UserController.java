package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * UserController контроллер для отображения видов модели User.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 20.03.2023
 */
@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String getLogin() {
        return "users/login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute User user,
                            Model model,
                            HttpServletRequest request) {
        var userOptional = userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Почта или пароль введены неверно");
            return "users/login";
        }
        var session = request.getSession();
        session.setAttribute("user", userOptional.get());
        return "redirect:/";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute User user,
                               Model model,
                               HttpServletRequest request) {
        var userOptional = userService.create(user);
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Пользователь с email:" + user.getEmail() + " зарегистрирован");
            return "users/register";
        }
        var session = request.getSession();
        session.setAttribute("user", userOptional.get());

        return "redirect:/";
    }


    @GetMapping("/register")
    public String gerRegister() {
        return "users/register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
