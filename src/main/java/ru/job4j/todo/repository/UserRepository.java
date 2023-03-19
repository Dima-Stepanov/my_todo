package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;

import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * UserRepository интерфейс описывает поведение хранилища модели User.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 19.03.2023
 */
public interface UserRepository {

    User create(User user);

    Optional<User> findByIdUser(int userId);

    void update(User user);

    void delete(int userId);

    Optional<User> findUserByEmailAndPassword(String email, String password);
}
