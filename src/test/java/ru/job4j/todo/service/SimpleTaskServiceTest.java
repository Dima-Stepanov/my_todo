package ru.job4j.todo.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    public void whenZoneIdSetUTCPlus2ThenReturnTimePlus2H() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 3, 31, 10, 0);
        var defTZ = TimeZone.getDefault().getID();
        var p2TZ = TimeZone.getTimeZone("Europe/Paris").getID();
        var expected = LocalDateTime.of(2023, 3, 31, 9, 0);
        var result = taskService.setTimeZone(localDateTime, defTZ, p2TZ);
        assertThat(result).isEqualToIgnoringSeconds(expected);
    }

    @Test
    public  void whenZoneIdPlus9setDefaultThenReturnTimeZoneDefault() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 3, 31, 19, 0);
        var defTZ = TimeZone.getDefault().getID();
        var p9TZ = TimeZone.getTimeZone("Asia/Tokyo").getID();
        var expected = LocalDateTime.of(2023, 3, 31, 13, 0);
        var result = taskService.setTimeZone(localDateTime, p9TZ, defTZ);
        assertThat(result).isEqualToIgnoringSeconds(expected);
    }
}