package ru.job4j.todo.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import ru.job4j.todo.configuration.HibernateConfiguration;
import ru.job4j.todo.model.Priority;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * HibernatePriorityRepository TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 03.04.2023
 */
class HibernatePriorityRepositoryTest {
    private static SessionFactory sf;
    private static CrudRepository crud;
    private static HibernatePriorityRepository priorityRepository;

    private static void deletePriority() {
        var session = sf.openSession();
        var transaction = session.beginTransaction();
        try (session) {
            var query = session.createQuery("delete from Task");
            query.executeUpdate();
            query = session.createQuery("delete from Priority");
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
        priorityRepository = new HibernatePriorityRepository(crud);
    }

    @AfterAll
    public static void closeResources() {
        sf.close();
    }

    @BeforeEach
    public void deleteBefore() {
        deletePriority();
    }

    @AfterEach
    public void deleteAfter() {
        deletePriority();
    }

    @Test
    public void whenFindByIdPriorityThenReturnPriorityOptional() {
        var priority = new Priority(0, "priority", 5);
        crud.run(session -> session.persist(priority));

        var expected = Optional.of(priority);
        var actual = priorityRepository.findPriorityById(priority.getId());

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void whenFindByIdPriorityThenReturnOptionalEmpty() {
        var actual = priorityRepository.findPriorityById(-1);

        assertThat(actual).isEmpty();
    }

    @Test
    public void whenFindAllPriorityThenReturnCollectionOrderByPosition() {
        var prior1 = new Priority(0, "prior1", 1);
        var prior2 = new Priority(0, "prior2", 2);
        var prior3 = new Priority(0, "prior3", 3);

        crud.run(session -> session.persist(prior3));
        crud.run(session -> session.persist(prior1));
        crud.run(session -> session.persist(prior2));

        var expected = List.of(prior1, prior2, prior3);
        var actual = priorityRepository.findAllPriorityOrderByPosition();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void whenFindAllPriorityThenReturnEmptyCollection() {
        var expected = Collections.emptyList();
        var actual = priorityRepository.findAllPriorityOrderByPosition();

        assertThat(actual).isEqualTo(expected);
    }
}