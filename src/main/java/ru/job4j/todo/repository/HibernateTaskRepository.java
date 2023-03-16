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
@Repository
@AllArgsConstructor
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
     * Обновление в базе задачи
     *
     * @param task обновленная задача
     */
    @Override
    public void update(Task task) {
        crudRepository.run(session -> session.merge(task));
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
                "from Task where id =:taskId",
                Task.class,
                Map.of("taskId", taskId)
        );
    }

    /**
     * Удаление задачи по ID
     *
     * @param taskId id задачи которую нужно удалить
     * @return boolean true false
     */
    @Override
    public boolean delete(int taskId) {
        return crudRepository.run(
                "delete from Task where id =:taskId",
                Map.of("taskId", taskId)
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
                "from Task order by id asc",
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
                "from Task where done is false order by id asc",
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
                "from Task where done true or done is null order by asc",
                Task.class
        );
    }
}
