package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.Collection;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * HibernateCategoryRepository реализация хранилища в базе данных модели Category,
 * при помощи hibernate.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 23.03.2023
 */
@Repository
@AllArgsConstructor
public class HibernateCategoryRepository implements CategoryRepository {
    private final CrudRepository crudRepository;

    /**
     * Метод возвращает список всех категорий из хранилища.
     *
     * @return Collection
     */
    @Override
    public Collection<Category> findAllCategoryOrderById() {
        return crudRepository.query(
                "from Category order by id asc",
                Category.class
        );
    }
}
