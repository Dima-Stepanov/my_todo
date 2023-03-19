package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * HibernateUserRepository реализация хранилища модели User в базе данных,
 * при помощи hibernate.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 19.03.2023
 */
@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {
    private final CrudRepository crudRepository;

    @Override
    public User create(User user) {
        crudRepository.run(session -> session.persist(user));
        return user;
    }

    @Override
    public Optional<User> findByIdUser(int userId) {
        return crudRepository.optional(
                "from User as u where u.id =:userId",
                User.class,
                Map.of("userId", userId)
        );
    }

    @Override
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }

    @Override
    public void delete(int userId) {
        crudRepository.run(
                "delete from User as u where u.id =:userId",
                Map.of("userId", userId)
        );
    }

    /**
     * Поиск пользователя по почте и паролю
     *
     * @param email    User email
     * @param password User password
     * @return Optional User
     */
    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return crudRepository.optional(
                "from User as u where u.email =:email and u.password =:password",
                User.class,
                Map.of(
                        "email", email,
                        "password", password
                )
        );
    }
}
