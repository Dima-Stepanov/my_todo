package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. ТОDO list
 * IndexController отображение вида стартовой страницы
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 17.03.2023
 */
@Controller
public class IndexController {
    /**
     * Метод отображает стартовую страницу.
     *
     * @return index.html
     */
    @GetMapping({"/", "index"})
    public String getIndexPage() {
        return "index";
    }
}
