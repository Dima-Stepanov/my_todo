package ru.job4j.todo.service;

import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.TimeZone;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * UserService interface описывает поведение логики обработки модели User
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 20.03.2023
 */
public interface UserService {
    Optional<User> create(User user);

    Optional<User> findByIdUser(int userId);

    boolean update(User user);

    boolean delete(int userId);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    Collection<TimeZone> getAllTimeZone();
}
