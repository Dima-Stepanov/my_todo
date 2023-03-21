package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    @GetMapping()
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
            model.addAttribute("message", "Задание № " + taskId + " не найдено");
            return "statuses/errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    /**
     * Метод обрабатывает get запрос,
     * на перевод задание в состояние выполнено.
     *
     * @param taskId int ID Task
     * @param model  Model
     * @return page tasks{id} or errors
     */
    @GetMapping("/doneTask/{taskId}")
    public String getDoneTask(@PathVariable int taskId, Model model) {
        var result = taskService.setStatusTaskById(taskId, true);
        if (!result) {
            model.addAttribute("message", "Задание № " + taskId + " не изменено");
            return "statuses/errors/404";
        }
        return "redirect:/tasks/" + taskId;
    }

    /**
     * Метод выполняет get запрос на удаление задания по ID
     *
     * @param taskId Task ID
     * @param model  Model
     * @return page tasks
     */
    @GetMapping("/delete/{taskId}")
    public String getDeleteTask(@PathVariable int taskId, Model model) {
        var result = taskService.delete(taskId);
        if (!result) {
            model.addAttribute("message", "Задание № " + taskId + " не  удалено");
            return "statuses/errors/404";
        }
        return "redirect:/tasks";
    }

    /**
     * Метод выполняет get запрос на отображение страницы редактирования задания.
     *
     * @param taskId Task ID
     * @param model  Model
     * @return Page tasks/edit or errors page
     */
    @GetMapping("/edit/{taskId}")
    public String getEditTask(@PathVariable int taskId, Model model) {
        var taskOptional = taskService.findTaskById(taskId);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание № " + taskId + " не найдено");
            return "statuses/errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/edit";
    }

    /**
     * Метод обрабатывает POST запрос на сохранение редактирования задания.
     *
     * @param task  Task
     * @param model Model
     * @return redirect:tasks/taskId or error page
     */
    @PostMapping("/update")
    public String postUpdateTask(@ModelAttribute Task task,
                                 Model model,
                                 HttpServletRequest request) {
        var session = request.getSession();
        var user = (User) session.getAttribute("user");
        task.setUser(user);
        var isUpdate = taskService.update(task);
        if (!isUpdate) {
            model.addAttribute("message", "Задание № "
                                          + task.getId()
                                          + " не изменено");
            return "statuses/errors/404";
        }
        return "redirect:/tasks/" + task.getId();
    }

    /**
     * Метод обрабатывает GET запрос на отображение вида создания нового задания
     *
     * @return tasks/created page.
     */
    @GetMapping("/create")
    public String getCreateTask() {
        return "tasks/create";
    }

    /**
     * Метод обрабатывает POST запрос на сохранение нового задания
     *
     * @param task new Task ID null
     * @return redirect:/tasks/taskId
     */
    @PostMapping("/create")
    public String postCreateTask(@ModelAttribute Task task,
                                 HttpServletRequest request) {
        var session = request.getSession();
        var user = (User) session.getAttribute("user");
        var priority = new Priority(1, "", 1);
        task.setPriority(priority);
        task.setUser(user);
        taskService.create(task);
        return "redirect:/tasks/" + task.getId();
    }
}
