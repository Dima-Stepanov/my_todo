package ru.job4j.todo.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 31.03.2023
 */
class SimpleTaskServiceTest {
    private static SimpleTaskService taskService;

    @BeforeAll
    public static void initService() {
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskService = new SimpleTaskService(taskRepository);
    }

    @Test
    public void testZoneID() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(ZoneOffset.UTC.getId());
        System.out.println(ZoneId.systemDefault().normalized().getId());
        System.out.println(ZoneId.of("UTC").getId());
        System.out.println(TimeZone.getDefault().getID());
        System.out.println(TimeZone.getTimeZone("UTC").getID());

        System.out.println(localDateTime);

        System.out.println(
                localDateTime.atZone(ZoneId.systemDefault())
                        .withZoneSameInstant(ZoneId.of("UTC"))
        );

        System.out.println(
                localDateTime.atZone(TimeZone.getDefault().toZoneId())
                        .withZoneSameInstant(ZoneId.of("UTC+9"))
        );
    }

}