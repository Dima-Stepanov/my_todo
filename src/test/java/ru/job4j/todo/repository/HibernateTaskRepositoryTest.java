package ru.job4j.todo.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import ru.job4j.todo.configuration.HibernateConfiguration;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 03.04.2023
 */
class HibernateTaskRepositoryTest {
    private static SessionFactory sf;
    private static CrudRepository crud;
    private static HibernateTaskRepository taskRepository;
    private static Priority priority;
    private static User user;

    private static void deleteTask() {
        var session = sf.openSession();
        var transaction = session.beginTransaction();
        try (session) {
            var query = session.createQuery("delete from Task");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @BeforeAll
    public static void initRepository() {
        sf = new HibernateConfiguration().getSessionFactory();
        crud = new CrudRepository(sf);
        taskRepository = new HibernateTaskRepository(crud);

        priority = new Priority(0, "prior", 2);
        user = new User(0, "name", "mail", "123", "UTC");
    }

    @AfterAll
    public static void closeResource() {
        sf.close();
    }

    @BeforeEach
    public void deleteBefore() {
        deleteTask();
    }

    @AfterEach
    public void deleteAfter() {
        deleteTask();
    }

    @Test
    void whenCreateTaskThenReturnTaskIdNotZero() {
        var task = new Task(
                0, "description",
                LocalDateTime.now(), false,
                user, priority,
                Collections.EMPTY_SET);
        var taskActual = taskRepository.create(task);

        assertThat(taskActual.getId()).isGreaterThan(0);
        assertThat(taskActual).usingRecursiveComparison().isEqualTo(task);
    }

    @Test
    void whenFindTaskByIdThenReturnOptionalTask() {
        var task = new Task(
                0, "description",
                LocalDateTime.now(), false,
                user, priority,
                Collections.EMPTY_SET);
        taskRepository.create(task);

        var expected = Optional.of(task);
        var actual = taskRepository.findTaskById(task.getId());

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void setStatusTaskById() {
    }

    @Test
    void findAllOrderById() {
    }

    @Test
    void findAllDoneOrderById() {
    }

    @Test
    void findAllNewOrderById() {
    }
}