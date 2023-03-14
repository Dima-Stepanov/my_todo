package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
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
    private final SessionFactory sessionFactory;

    /**
     * Сохранить в базе
     *
     * @param task задача
     * @return Task задача с id
     */
    @Override
    public Task create(Task task) {
        return null;
    }

    /**
     * Обновление в базе задачи
     *
     * @param task обновленная задача
     * @return boolean true false
     */
    @Override
    public boolean update(Task task) {
        return false;
    }

    /**
     * Поиск задачи по ID
     *
     * @param taskId ID задачи
     * @return Optional задачи
     */
    @Override
    public Optional<Task> findTaskById(int taskId) {
        return Optional.empty();
    }

    /**
     * Удаление задачи по ID
     *
     * @param taskId id задачи которую нужно удалить
     * @return boolean true false
     */
    @Override
    public boolean delete(int taskId) {
        return false;
    }

    /**
     * Список всех задач в системе
     *
     * @return список всех задач
     */
    @Override
    public Collection<Task> findAll() {
        return null;
    }

    /**
     * Список завершённых задач
     * отсортированных по дате создания от новых к старым
     *
     * @return список завершённых задач.
     */
    @Override
    public Collection<Task> findAllDoneOrderByCrete() {
        return null;
    }

    /**
     * Список не завершённых задач
     * отсортированных по дате создания от новых к старым.
     *
     * @return список новых задач
     */
    @Override
    public Collection<Task> findAllNewOrderByCrete() {
        return null;
    }
}
