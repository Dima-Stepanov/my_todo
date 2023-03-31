package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    boolean update(Task task, Set<Integer> categoryId);

    boolean delete(int taskId);

    boolean setStatusTaskById(int taskId, boolean status);

    Collection<Task> findAllOrderById(String userZoneId);

    Collection<Task> findAllDoneOrderById(String userZoneId);

    Collection<Task> findAllNewOrderById(String userZoneId);

    /**
     * Метод устанавливает значение categories
     * Преобразовывая Set<Integer> iDs в new Category(id)
     *
     * @param task       Task
     * @param categoryId Set<Integer>
     * @return Task
     */
    default Task setCategories(Task task, Set<Integer> categoryId) {
        Set<Category> categories = new HashSet<>();
        if (categoryId != null && !categoryId.isEmpty()) {
            categories = categoryId.stream()
                    .map(id -> new Category(id, null))
                    .collect(Collectors.toSet());
        }
        task.setCategories(categories);
        return task;
    }

    default Task setPriorityCategories(Task task, Set<Integer> categoryId, int priorityId) {
        Set<Category> categories = new HashSet<>();
        setCategories(task, categoryId);
        if (priorityId > 0) {
            task.setPriority(new Priority(priorityId, null, priorityId));
        }
        return task;
    }
}
