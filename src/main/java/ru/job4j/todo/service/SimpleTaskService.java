package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. ТОDO list
 * SimpleTaskService реализация бизнес логики обработки модели Task.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 17.03.2023
 */
@Service
@AllArgsConstructor
@Slf4j
public class SimpleTaskService implements TaskService {
    private final TaskRepository taskRepository;
    private final String systemZoneId = TimeZone.getDefault().getID();

    @Override
    public Task create(Task task, Set<Integer> categoryId, int priorityId) {
        setPriorityCategories(task, categoryId, priorityId);
        return taskRepository.create(task);
    }

    @Override
    public Optional<Task> findTaskById(int taskId, String userTimeZone) {
        var taskOptional = taskRepository.findTaskById(taskId);
        if (taskOptional.isPresent()) {
            var timeByZone = setTimeZone(taskOptional.get().getCreated(),
                    systemZoneId,
                    userTimeZone
            );
            taskOptional.get().setCreated(timeByZone);
        }
        return taskOptional;
    }

    @Override
    public boolean update(Task task, Set<Integer> categoryId, String userTimeZone) {
        task.setCreated(
                setTimeZone(
                        task.getCreated(),
                        userTimeZone,
                        systemZoneId
                )
        );
        setCategories(task, categoryId);
        try {
            taskRepository.update(task);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int taskId) {
        try {
            taskRepository.delete(taskId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean setStatusTaskById(int taskId, boolean status) {
        try {
            taskRepository.setStatusTaskById(taskId, status);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Collection<Task> findAllOrderById(String userZoneId) {
        return setTimeZone(
                taskRepository.findAllOrderById(),
                systemZoneId,
                userZoneId);
    }

    @Override
    public Collection<Task> findAllDoneOrderById(String userZoneId) {
        return setTimeZone(
                taskRepository.findAllDoneOrderById(),
                systemZoneId,
                userZoneId);
    }

    @Override
    public Collection<Task> findAllNewOrderById(String userZoneId) {
        return setTimeZone(
                taskRepository.findAllNewOrderById(),
                systemZoneId,
                userZoneId);
    }

    /**
     * Метод приводит Дату время относительно системного часового пояса,
     * к часовому поясу пользователя
     *
     * @param dateTime     LocalDateTime
     * @param systemZoneId System zone ID
     * @param userTimeZone User zone ID
     * @return LocalDateTime to User zone ID
     */
    public LocalDateTime setTimeZone(LocalDateTime dateTime, String systemZoneId, String userTimeZone) {
        if (userTimeZone.isEmpty()) {
            userTimeZone = systemZoneId;
        }
        var dateTimeZone = dateTime
                .atZone(ZoneId.of(systemZoneId))
                .withZoneSameInstant(ZoneId.of(userTimeZone));
        return dateTimeZone.toLocalDateTime();
    }

    /**
     * Метод изменяет коллекцию заданий, приводя дату время к часовому пользователю
     *
     * @param tasks        Collection Task
     * @param systemZoneId String
     * @param userTimeZone String
     * @return Collection task
     */
    public Collection<Task> setTimeZone(Collection<Task> tasks, String systemZoneId, String userTimeZone) {
        return tasks.stream()
                .peek(task -> task.setCreated(setTimeZone(task.getCreated(), systemZoneId, userTimeZone)))
                .collect(Collectors.toList());
    }

    /**
     * Метод устанавливает значение categories
     * Преобразовывая Set<Integer> iDs в new Category(id)
     *
     * @param task       Task
     * @param categoryId Set<Integer>
     * @return Task
     */
    public Task setCategories(Task task, Set<Integer> categoryId) {
        Set<Category> categories = new HashSet<>();
        if (categoryId != null && !categoryId.isEmpty()) {
            categories = categoryId.stream()
                    .map(id -> new Category(id, null))
                    .collect(Collectors.toSet());
        }
        task.setCategories(categories);
        return task;
    }

    public Task setPriorityCategories(Task task, Set<Integer> categoryId, int priorityId) {
        setCategories(task, categoryId);
        if (priorityId > 0) {
            task.setPriority(new Priority(priorityId, null, priorityId));
        }
        return task;
    }
}
