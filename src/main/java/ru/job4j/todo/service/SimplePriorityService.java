package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * SimplePriorityService реализация бизнес логики обработки модели Priority.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 23.03.2023
 */
@AllArgsConstructor
@Service
public class SimplePriorityService implements PriorityService {
    private final PriorityRepository priorityRepository;

    @Override
    public Optional<Priority> findPriorityById(int priorityId) {
        return priorityRepository.findPriorityById(priorityId);
    }

    @Override
    public Collection<Priority> findAllPriorityOrderById() {
        return priorityRepository.findAllPriorityOrderByPosition();
    }
}
