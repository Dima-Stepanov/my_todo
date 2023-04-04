package ru.job4j.todo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * UserController TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 04.04.2023
 */
class UserControllerTest {
    private UserService userService = mock(UserService.class);
    private UserController userController = new UserController(userService);

    @Test
    void whenGetLoginThenReturnLoginPage() {
        var expected = "users/login";
        var actual = userController.getLogin();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenPostLoginThenReturnIndexPage() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        when(userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(Optional.of(user));
        MockHttpServletRequest request = new MockHttpServletRequest();

        var model = new ConcurrentModel();
        var actual = userController.postLogin(user, model, request);
        var expected = "redirect:/";
        var actualUserSession = request.getSession().getAttribute("user");

        assertThat(actualUserSession).usingRecursiveComparison().isEqualTo(user);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenPostLoginThenReturnErrorPageAndMessage() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        var errorMessage = "Почта или пароль введены неверно";
        when(userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(Optional.empty());
        var httpRequest = mock(HttpServletRequest.class);

        var model = new ConcurrentModel();
        var actualPage = userController.postLogin(user, model, httpRequest);
        var expectedPage = "users/login";
        var actualMessage = model.getAttribute("error");

        assertThat(actualPage).isEqualTo(expectedPage);
        assertThat(actualMessage).isEqualTo(errorMessage);
    }

    @Test
    void whenPostRegisterThenReturnRedirectStartPage() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        when(userService.create(user)).thenReturn(Optional.of(user));
        MockHttpServletRequest request = new MockHttpServletRequest();

        var model = new ConcurrentModel();
        var actualPage = userController.postRegister(user, model, request);
        var actualUsersSession = request.getSession().getAttribute("user");
        var expectedPage = "redirect:/";

        assertThat(actualPage).isEqualTo(expectedPage);
        assertThat(actualUsersSession).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void whenPostRegisterThenReturnRedirectErrorMassage() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        var errorMessage = "Пользователь с email:" + user.getEmail() + " зарегистрирован";
        when(userService.create(user)).thenReturn(Optional.empty());
        MockHttpServletRequest request = new MockHttpServletRequest();

        var model = new ConcurrentModel();
        var actualPage = userController.postRegister(user, model, request);
        var actualMessage = model.getAttribute("error");
        var expectedPage = "users/register";

        assertThat(actualPage).isEqualTo(expectedPage);
        assertThat(actualMessage).isEqualTo(errorMessage);
    }

    @Test
    void whenGerRegisterThenReturnRegisterPage() {
        var model = new ConcurrentModel();
        var actualPage = userController.gerRegister(model);
        var expectPage = "users/register";

        assertThat(actualPage).isEqualTo(expectPage);
    }

    @Test
    void whenLogoutThenClearSessionReturnLoginPage() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("user", user);

        var actualPage = userController.logout(httpSession);
        var expectedPage = "redirect:/users/login";

        assertThat(actualPage).isEqualTo(expectedPage);
        assertThat(httpSession.isInvalid()).isTrue();
    }
}