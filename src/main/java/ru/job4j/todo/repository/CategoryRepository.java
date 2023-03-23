package ru.job4j.todo.repository;

import ru.job4j.todo.model.Category;

import java.util.Collection;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * CategoryRepository interface описывает поведение хранилища модели Category
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 23.03.2023
 */
public interface CategoryRepository {
    /**
     * Метод возвращает список всех категорий из хранилища.
     *
     * @return Collection
     */
    Collection<Category> findAllCategoryOrderById();
}
