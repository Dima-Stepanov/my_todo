package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.Collection;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * SimpleCategoryService реализация бизнес логики обработки модели Category.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 23.03.2023
 */
@AllArgsConstructor
@Service
public class SimpleCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Collection<Category> findAllCategoryOrderById() {
        return categoryRepository.findAllCategoryOrderById();
    }
}
