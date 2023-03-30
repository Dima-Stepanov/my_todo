package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;

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
    private final ZoneId systemZoneId = TimeZone.getDefault().toZoneId();

    @Override
    public Task create(Task task, Set<Integer> categoryId, int priorityId) {
        setPriorityCategories(task, categoryId, priorityId);
        return taskRepository.create(task);
    }

    @Override
    public Optional<Task> findTaskById(int taskId) {
        var taskOptional = taskRepository.findTaskById(taskId);
        if (taskOptional.isPresent()) {
            var timeByZone = setTimeZone(taskOptional.get().getCreated(),
                    systemZoneId,
                   TimeZone.getTimeZone(taskOptional.get().getUser().getTimeZone())
                           .toZoneId()
            );
            taskOptional.get().setCreated(timeByZone);
        }
        return taskRepository.findTaskById(taskId);
    }

    @Override
    public boolean update(Task task, Set<Integer> categoryId) {
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
    public Collection<Task> findAllOrderById() {
        return taskRepository.findAllOrderById();
    }

    @Override
    public Collection<Task> findAllDoneOrderById() {
        return taskRepository.findAllDoneOrderById();
    }

    @Override
    public Collection<Task> findAllNewOrderById() {
        return taskRepository.findAllNewOrderById();
    }

    /**
     * Метод приводит Дату время относительно системного часового пояса,
     * к часовому поясу пользователя
     *
     * @param dateTime   Edit LocalDateTime
     * @param systemZone current ZoneID
     * @param userZone   user ZoneID
     * @return dateTimeZone LocalDateTimeZone
     */
    private LocalDateTime setTimeZone(LocalDateTime dateTime, ZoneId systemZone, ZoneId userZone) {
        log.info("Time of ZoneID default: {}", dateTime);
        var dateTimeZone = dateTime.atZone(
                systemZone
        ).withZoneSameInstant(userZone).toLocalDateTime();
        log.info("Date of ZoneID User: {}", dateTimeZone);
        return dateTimeZone;
    }
}
