package ru.job4j.todo.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import ru.job4j.todo.configuration.HibernateConfiguration;
import ru.job4j.todo.model.Category;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    private static HibernateCategoryRepository categoryRepository;

    private static void delete() {
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
        sf = HibernateConfigurationFromTest.getSFFromTest();
        var crud = new CrudRepository(sf);
        categoryRepository = new HibernateCategoryRepository(crud);
    }

    @AfterAll
    static void closeSF() {
        sf.close();
    }

    @BeforeEach
    public void deleteBefore() {
        delete();
    }


    @AfterEach
    public void deleteAfter() {
        delete();
    }

    @Test
    public void whenGetAllCategoryThenReturnAllCategory() {
        var cat1 = new Category(0, "cat1");
        var cat2 = new Category(0, "cat2");
        var cat3 = new Category(0, "cat3");
        var crud = new CrudRepository(sf);
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