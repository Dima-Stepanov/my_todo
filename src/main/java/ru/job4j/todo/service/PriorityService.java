package ru.job4j.todo.service;

import ru.job4j.todo.model.Priority;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * PriorityService interface описывает поведение слоя сервиса управления моделью Priority
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 23.03.2023
 */
public interface PriorityService {
    Optional<Priority> findPriorityById(int priorityId);

    Collection<Priority> findAllPriorityOrderById();
}
