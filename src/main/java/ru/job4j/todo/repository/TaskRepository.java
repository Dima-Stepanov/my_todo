package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * TaskRepository interface описывает поведение хранилища модели Task
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 14.03.2023
 */
public interface TaskRepository {
    /**
     * Сохранить в базе
     *
     * @param task задача
     * @return Task задача с id
     */
    Task create(Task task);

    /**
     * Обновление в базе задачи
     *
     * @param task обновленная задача
     * @return boolean true false
     */
    boolean update(Task task);

    /**
     * Поиск задачи по ID
     *
     * @param taskId ID задачи
     * @return Optional задачи
     */
    Optional<Task> findTaskById(int taskId);

    /**
     * Удаление задачи по ID
     *
     * @param taskId id задачи которую нужно удалить
     * @return boolean true false
     */
    boolean delete(int taskId);

    /**
     * Список всех задач в системе
     *
     * @return список всех задач
     */
    Collection<Task> findAll();

    /**
     * Список завершённых задач
     * отсортированных по дате создания от новых к старым
     *
     * @return список завершённых задач.
     */
    Collection<Task> findAllDoneOrderByCrete();

    /**
     * Список не завершённых задач
     * отсортированных по дате создания от новых к старым.
     *
     * @return список новых задач
     */
    Collection<Task> findAllNewOrderByCrete();
}
