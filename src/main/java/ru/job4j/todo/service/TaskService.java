package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. ТОDO list
 * TaskService interface описывает поведение слоя сервиса управления моделью Task
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 17.03.2023
 */
public interface TaskService {

    Task create(Task task, Set<Integer> categoryId, int priorityId);

    Optional<Task> findTaskById(int taskId, String userTimeZone);

    boolean update(Task task, Set<Integer> categoryId, String userTimeZone);

    boolean delete(int taskId);

    boolean setStatusTaskById(int taskId, boolean status);

    Collection<Task> findAllOrderById(String userZoneId);

    Collection<Task> findAllDoneOrderById(String userZoneId);

    Collection<Task> findAllNewOrderById(String userZoneId);
}
