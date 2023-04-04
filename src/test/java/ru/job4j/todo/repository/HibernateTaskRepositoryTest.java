package ru.job4j.todo.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import ru.job4j.todo.configuration.HibernateConfiguration;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    private static HibernateTaskRepository taskRepository;
    private Priority priority;
    private User user;
    private Category category;

    private static void deleteTask() {
        var crud = new CrudRepository(sf);
        crud.run("delete from Task as t where t.id >:tId",
                Map.of("tId", 0));
        crud.run("delete from Priority as p where p.id >:pId",
                Map.of("pId", 0));
        crud.run("delete from Category as c where c.id >:cId",
                Map.of("cId", 0));
        crud.run("delete from User as u where u.id >:uId",
                Map.of("uId", 0));
    }

    @BeforeAll
    public static void initRepository() {
        sf = new HibernateConfiguration().getSessionFactory();
        var crud = new CrudRepository(sf);
        taskRepository = new HibernateTaskRepository(crud);
    }

    @AfterAll
    public static void closeResource() {
        sf.close();
    }

    @BeforeEach
    public void deleteBefore() {
        deleteTask();

        var crud = new CrudRepository(sf);
        this.priority = new Priority(0, "prior", 2);
        this.user = new User(0, "name", "mail", "123", "UTC");
        this.category = new Category(0, "category");

        crud.run(session -> session.persist(user));
        crud.run(session -> session.persist(priority));
        crud.run(session -> session.persist(category));
    }

    @AfterEach
    public void deleteAfter() {
        deleteTask();
    }

    @Test
    void whenCreateTaskThenReturnTaskIdNotZero() {
        var task = new Task(
                0, "description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false,
                user, priority,
                Set.of(category));
        var taskActual = taskRepository.create(task);

        assertThat(taskActual.getId()).isGreaterThan(0);
        assertThat(taskActual).usingRecursiveComparison().isEqualTo(task);
    }

    @Test
    void whenFindTaskByIdThenReturnOptionalTask() {
        var task = new Task(
                0, "description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false,
                user, priority,
                Set.of(category));
        taskRepository.create(task);

        var expected = Optional.of(task);
        var actual = taskRepository.findTaskById(task.getId());

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenFindTaskByIdThenReturnOptionalEmpty() {
        var actual = taskRepository.findTaskById(23);
        assertThat(actual).isEmpty();
    }

    @Test
    void whenUpdateTaskThenFindByIdReturnUpdateTask() {
        var task = new Task(
                0, "description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false,
                user, priority,
                Set.of(category));
        taskRepository.create(task);
        task.setDescription("new Description");
        task.setDone(true);

        taskRepository.update(task);

        var actual = taskRepository.findTaskById(task.getId());

        assertThat(actual.get()).usingRecursiveComparison().isEqualTo(task);
    }

    @Test
    void whenDeleteTaskByIdThenFindByIdEmpty() {
        var task = new Task(
                0, "description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false,
                user, priority,
                Set.of(category));
        taskRepository.create(task);

        taskRepository.delete(task.getId());

        var actual = taskRepository.findTaskById(task.getId());

        assertThat(actual).isEmpty();
    }

    @Test
    void whenSetStatusTaskByIdThenReturnTaskNewStatus() {
        var task = new Task(
                0, "description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false,
                user, priority,
                Set.of(category));
        taskRepository.create(task);

        taskRepository.setStatusTaskById(task.getId(), true);

        var actualTask = taskRepository.findTaskById(task.getId());

        assertThat(actualTask.get().isDone()).isTrue();
    }

    @Test
    void whenFindAllOrderByIdThenReturnCollectionTask() {
        var task1 = new Task(
                0, "description1",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), true,
                user, priority,
                Set.of(category));
        var task2 = new Task(
                0, "description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false,
                user, priority,
                Set.of(category));
        taskRepository.create(task2);
        taskRepository.create(task1);

        var expected = List.of(task2, task1);
        var actual = taskRepository.findAllOrderById();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenFindAllOrderByIdThenReturnCollectionEmpty() {
        var actual = taskRepository.findAllOrderById();
        assertThat(actual).isEqualTo(Collections.emptyList());
    }

    @Test
    void whenFindAllDoneOrderByIdThenReturnCollectionTaskIsDoneTrue() {
        var task1 = new Task(
                0, "description1",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), true,
                user, priority,
                Set.of(category));
        var task2 = new Task(
                0, "description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false,
                user, priority,
                Set.of(category));
        taskRepository.create(task2);
        taskRepository.create(task1);

        var expected = List.of(task1);
        var actual = taskRepository.findAllDoneOrderById();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenFindAllDoneOrderByIdThenReturnCollectionEmpty() {
        var actual = taskRepository.findAllDoneOrderById();
        assertThat(actual).isEqualTo(Collections.emptyList());
    }

    @Test
    void whenFindAllNewOrderByIdThenReturnCollectionTaskIsDoneFalse() {
        var task1 = new Task(
                0, "description1",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), true,
                user, priority,
                Set.of(category));
        var task2 = new Task(
                0, "description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false,
                user, priority,
                Set.of(category));
        taskRepository.create(task2);
        taskRepository.create(task1);

        var expected = List.of(task2);
        var actual = taskRepository.findAllNewOrderById();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenFindAllNewOrderByIdThenReturnCollectionEmpty() {
        var actual = taskRepository.findAllNewOrderById();
        assertThat(actual).isEqualTo(Collections.emptyList());
    }
}