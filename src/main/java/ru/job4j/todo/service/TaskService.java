package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

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

    Task create(Task task);

    Optional<Task> findTaskById(int taskId);

    boolean update(Task task);

    boolean delete(int taskId);

    boolean setStatusTaskById(int taskId, boolean status);

    Collection<Task> findAllOrderById();

    Collection<Task> findAllDoneOrderById();

    Collection<Task> findAllNewOrderById();
}
