package ru.job4j.todo.repository;

import ru.job4j.todo.model.Priority;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * PriorityRepository interface описывает поведение хранилища модели Priority
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 23.03.2023
 */
public interface PriorityRepository {
    /**
     * Поиск приоритета по ID
     *
     * @param priorityId Priority ID
     * @return Optional Priority
     */
    Optional<Priority> findPriorityById(int priorityId);

    /**
     * Выгрузка всех видов приоритетов
     *
     * @return Collection Priority
     */
    Collection<Priority> findAllPriorityOrderByPosition();
}
