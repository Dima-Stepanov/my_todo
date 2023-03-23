package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;

import java.util.Collection;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * CategoryService interface описывает поведение слоя сервиса управления моделью Category
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 23.03.2023
 */
public interface CategoryService {
    Collection<Category> findAllCategoryOrderById();
}
