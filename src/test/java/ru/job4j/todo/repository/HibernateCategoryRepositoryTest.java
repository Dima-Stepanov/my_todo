package ru.job4j.todo.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.configuration.HibernateConfiguration;
import ru.job4j.todo.model.Category;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * HibernateCategoryRepository TEST.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 03.04.2023
 */
class HibernateCategoryRepositoryTest {
    private static SessionFactory sf;
    private static CrudRepository crud;
    private static HibernateCategoryRepository categoryRepository;

    private static void clearCategory() {
        var session = sf.openSession();
        var transaction = session.beginTransaction();
        try (session) {
            var query = session.createQuery("delete from Category");
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
        categoryRepository = new HibernateCategoryRepository(crud);
    }

    @BeforeEach
    public void deleteBefore() {
        clearCategory();
    }


    @AfterEach
    public void deleteAfter() {
        clearCategory();
    }

    @Test
    public void whenGetAllCategoryThenReturnAllCategory() {
        var cat1 = new Category(0, "cat1");
        var cat2 = new Category(0, "cat2");
        var cat3 = new Category(0, "cat3");
        crud.run(session -> session.persist(cat1));
        crud.run(session -> session.persist(cat2));
        crud.run(session -> session.persist(cat3));
        var expected = List.of(cat1, cat2, cat3);
        var result = categoryRepository.findAllCategoryOrderById();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void whenGetAllCategoryThenReturnEmpty() {
        var expected = Collections.emptyList();
        var result = categoryRepository.findAllCategoryOrderById();
        assertThat(result).isEqualTo(expected);
    }
}