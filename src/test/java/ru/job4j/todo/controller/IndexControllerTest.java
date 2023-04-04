package ru.job4j.todo.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 04.04.2023
 */
class IndexControllerTest {
    private IndexController indexController = new IndexController();

    @Test
    void getIndexPage() {
        var expected = "index";
        var actual = indexController.getIndexPage();

        assertThat(actual).isEqualTo(expected);
    }
}