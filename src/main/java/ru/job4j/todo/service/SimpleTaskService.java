package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.Collection;
import java.util.Optional;

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
public class SimpleTaskService implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public Task create(Task task) {
        return taskRepository.create(task);
    }

    @Override
    public Optional<Task> findTaskById(int taskId) {
        return taskRepository.findTaskById(taskId);
    }

    @Override
    public boolean update(Task task) {
        return taskRepository.update(task);
    }

    @Override
    public boolean delete(int taskId) {
        return taskRepository.delete(taskId);
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
}
