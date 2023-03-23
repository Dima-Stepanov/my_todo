package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * HibernatePriorityRepository реализация хранилища в базе данных модели Priority,
 * при помощи hibernate.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 23.03.2023
 */
@AllArgsConstructor
@Repository
public class HibernatePriorityRepository implements PriorityRepository {
    private final CrudRepository crudRepository;

    /**
     * Поиск приоритета по ID
     *
     * @param priorityId Priority ID
     * @return Optional Priority
     */
    @Override
    public Optional<Priority> findPriorityById(int priorityId) {
        return crudRepository.optional(
                "from Priority as p where p.id =:priorityId",
                Priority.class,
                Map.of("priorityId", priorityId)
        );
    }

    /**
     * Выгрузка всех видов приоритетов
     *
     * @return Collection Priority
     */
    @Override
    public Collection<Priority> findAllPriorityOrderByPosition() {
        return crudRepository.query(
                "from Priority order by id asc",
                Priority.class
        );
    }
}
