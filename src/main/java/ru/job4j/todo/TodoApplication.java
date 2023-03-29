package ru.job4j.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 14.03.2023
 */
@SpringBootApplication
public class TodoApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoApplication.class.getSimpleName());
    private static final String START_PAGE = "http://localhost:8080/index";

    @PostConstruct
    void initTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
        LOGGER.info("Go to: {}", START_PAGE);
    }
}

