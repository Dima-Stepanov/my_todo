package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.service.TaskService;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. ТОDO list
 * TaskController контроллер обработки видов для отображения модели Task.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 17.03.2023
 */
@AllArgsConstructor
@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    /**
     * Отображает вид всеми задачами.
     *
     * @param model Model
     * @return allTasks.html
     */
    @GetMapping("/all")
    public String getAllTasks(Model model) {
        var allTasks = taskService.findAllOrderById();
        model.addAttribute("tasksList", allTasks);
        return "tasks/allTasks";
    }

    /**
     * Отображает вид завершенными задачами
     *
     * @param model Model
     * @return doneTasks.html
     */
    @GetMapping("/done")
    public String getAllDoneTasks(Model model) {
        var doneTasks = taskService.findAllDoneOrderById();
        model.addAttribute("doneTasks", doneTasks);
        return "tasks/doneTasks";
    }

    /**
     * Отображает вид завершенными задачами
     *
     * @param model Model
     * @return doneTasks.html
     */
    @GetMapping("/new")
    public String getAllNewTasks(Model model) {
        var newTasks = taskService.findAllNewOrderById();
        model.addAttribute("newTasks", newTasks);
        return "tasks/newTasks";
    }

    /**
     * Отображает вид одно задание по id
     */
    @GetMapping("{taskId}")
    public String getOneTask(@PathVariable int taskId, Model model) {
        var taskOptional = taskService.findTaskById(taskId);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание № " + taskId + " недоступно");
            return "statuses/errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }
}
