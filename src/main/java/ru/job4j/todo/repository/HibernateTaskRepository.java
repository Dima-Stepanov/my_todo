package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * HibernateTaskRepository реализация хранилища в базе данных модели Task,
 * при помощи hibernate.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 14.03.2023
 */
@AllArgsConstructor
@Repository
public class HibernateTaskRepository implements TaskRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе
     *
     * @param task задача
     * @return Task задача с id
     */
    @Override
    public Task create(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    /**
     * Поиск задачи по ID
     *
     * @param taskId ID задачи
     * @return Optional задачи
     */
    @Override
    public Optional<Task> findTaskById(int taskId) {
        return crudRepository.optional(
                """
                        FROM Task AS t 
                        JOIN FETCH t.priority AS p 
                        LEFT JOIN FETCH t.categories AS c
                        WHERE t.id = :taskId
                        """,
                Task.class,
                Map.of("taskId", taskId)
        );
    }

    /**
     * Обновление в базе задачи
     *
     * @param task обновленная задача
     */
    @Override
    public void update(Task task) {
        crudRepository.run(session -> session.update(task));
    }

    /**
     * Удаление задачи по ID
     *
     * @param taskId id задачи которую нужно удалить
     */
    @Override
    public void delete(int taskId) {
        crudRepository.run(
                "delete from Task where id = :taskId",
                Map.of("taskId", taskId)
        );
    }

    /**
     * Изменяет статус задачи.
     * Выполнена status = true, set done = true.
     * Активна status = false, set done = false.
     *
     * @param taskId ID Task
     */
    @Override
    public void setStatusTaskById(int taskId, boolean status) {
        crudRepository.run(
                """
                        UPDATE Task AS t 
                        SET t.done =:status 
                        WHERE t.id =:taskId
                        """,
                Map.of("status", status,
                        "taskId", taskId)
        );
    }

    /**
     * Список всех задач в системе
     * отсортированных по ID
     *
     * @return список всех задач
     */
    @Override
    public Collection<Task> findAllOrderById() {
        return crudRepository.query(
                """
                        SELECT DISTINCT t 
                        FROM Task AS t 
                        JOIN FETCH t.priority AS p
                        LEFT JOIN FETCH t.categories AS c
                        ORDER BY t.id ASC
                        """,
                Task.class
        );
    }

    /**
     * Список завершённых задач
     * отсортированных по ID
     *
     * @return список завершённых задач.
     */
    @Override
    public Collection<Task> findAllDoneOrderById() {
        return crudRepository.query(
                """
                        SELECT DISTINCT t 
                        FROM Task AS t 
                        LEFT JOIN FETCH t.priority AS p 
                        LEFT JOIN FETCH t.categories AS c 
                        WHERE t.done IS true 
                        ORDER BY t.id ASC
                        """,
                Task.class
        );
    }

    /**
     * Список не завершённых задач
     * отсортированных по ID.
     *
     * @return список новых задач
     */
    @Override
    public Collection<Task> findAllNewOrderById() {
        return crudRepository.query(
                """
                        SELECT DISTINCT t 
                        FROM Task AS t 
                        LEFT JOIN FETCH t.priority AS p 
                        LEFT JOIN FETCH t.categories AS c 
                        WHERE t.done IS false 
                        ORDER BY t.id ASC
                        """,
                Task.class
        );
    }
}
