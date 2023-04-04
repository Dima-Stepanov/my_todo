package ru.job4j.todo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 04.04.2023
 */
class TaskControllerTest {
    private TaskService taskService;
    private PriorityService priorityService;
    private CategoryService categoryService;
    private TaskController taskController;

    @BeforeEach
    public void initController() {
        taskService = mock(TaskService.class);
        priorityService = mock(PriorityService.class);
        categoryService = mock(CategoryService.class);
        taskController = new TaskController(taskService, priorityService, categoryService);
    }

    @Test
    void whenGetAllTasksThenReturnAllTaskPage() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);
        when(taskService.findAllOrderById(user.getTimeZone())).thenReturn(Collections.emptyList());

        var model = new ConcurrentModel();
        var actualPage = taskController.getAllTasks(model, session);
        var expectedPage = "tasks/allTasks";
        var actualAllTask = model.getAttribute("tasksList");

        assertThat(actualPage).isEqualTo(expectedPage);
        assertThat(actualAllTask).isEqualTo(Collections.emptyList());
    }

    @Test
    void whenGetAllDoneTasksThenReturnAllDoneTaskPage() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);
        when(taskService.findAllDoneOrderById(user.getTimeZone())).thenReturn(Collections.emptyList());

        var model = new ConcurrentModel();
        var actualPage = taskController.getAllDoneTasks(model, session);
        var expectedPage = "tasks/doneTasks";
        var actualAllTask = model.getAttribute("doneTasks");

        assertThat(actualPage).isEqualTo(expectedPage);
        assertThat(actualAllTask).isEqualTo(Collections.emptyList());
    }

    @Test
    void whenGetAllNewTasksThenReturnAllNewTaskPage() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);
        when(taskService.findAllNewOrderById(user.getTimeZone())).thenReturn(Collections.emptyList());

        var model = new ConcurrentModel();
        var actualPage = taskController.getAllNewTasks(model, session);
        var expectedPage = "tasks/newTasks";
        var actualAllTask = model.getAttribute("newTasks");

        assertThat(actualPage).isEqualTo(expectedPage);
        assertThat(actualAllTask).isEqualTo(Collections.emptyList());
    }

    @Test
    void whenGetOneTaskThenReturnOneTaskPage() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        var priority = new Priority(1, "priority", 5);
        var category = new Category(1, "category");
        var task = new Task(1, "description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), true,
                user, priority, Set.of(category));

        when(taskService.findTaskById(task.getId(), user.getTimeZone())).thenReturn(Optional.of(task));
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        var model = new ConcurrentModel();
        var actualPage = taskController.getOneTask(task.getId(), model, session);
        var expectPage = "tasks/one";
        var actualTask = model.getAttribute("task");

        assertThat(actualPage).isEqualTo(expectPage);
        assertThat(actualTask).usingRecursiveComparison().isEqualTo(task);
    }

    @Test
    void whenGetOneTaskThenReturnErrorsPageAndMessage() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        var taskId = 99;
        when(taskService.findTaskById(taskId, user.getTimeZone())).thenReturn(Optional.empty());
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        var model = new ConcurrentModel();
        var actualPage = taskController.getOneTask(taskId, model, session);
        var expectPage = "statuses/errors/404";
        var actualMessage = model.getAttribute("message");
        var expectMessage = "Задание № " + taskId + " не найдено";

        assertThat(actualPage).isEqualTo(expectPage);
        assertThat(actualMessage).isEqualTo(expectMessage);
    }

    @Test
    void whenGetDoneTaskThenReturnPageDoneTask() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        var priority = new Priority(1, "priority", 5);
        var category = new Category(1, "category");
        var task = new Task(1, "description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false,
                user, priority, Set.of(category));
        when(taskService.setStatusTaskById(task.getId(), true)).thenReturn(true);

        var model = new ConcurrentModel();
        var actualPage = taskController.getDoneTask(task.getId(), model);
        var expectPage = "redirect:/tasks/" + task.getId();

        assertThat(actualPage).isEqualTo(expectPage);
    }

    @Test
    void whenGetDoneTaskThenReturnErrorPageAndMessage() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        var priority = new Priority(1, "priority", 5);
        var category = new Category(1, "category");
        var task = new Task(1, "description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false,
                user, priority, Set.of(category));
        when(taskService.setStatusTaskById(task.getId(), true)).thenReturn(false);

        var model = new ConcurrentModel();
        var actualPage = taskController.getDoneTask(task.getId(), model);
        var expectPage = "statuses/errors/404";
        var actualMessage = model.getAttribute("message");
        var expectMessage = "Задание № " + task.getId() + " не изменено";

        assertThat(actualPage).isEqualTo(expectPage);
        assertThat(actualMessage).isEqualTo(expectMessage);
    }

    @Test
    void whenGetDeleteTaskThenReturnAllTaskPage() {
        var expectPage = "redirect:/tasks";
        when(taskService.delete(anyInt())).thenReturn(true);

        var model = new ConcurrentModel();
        var actualPage = taskController.getDeleteTask(anyInt(), model);

        assertThat(actualPage).isEqualTo(expectPage);
    }

    @Test
    void whenGetDeleteTaskThenReturnErrorPage() {
        int taskId = 99;
        when(taskService.delete(taskId)).thenReturn(false);

        var model = new ConcurrentModel();
        var actualPage = taskController.getDeleteTask(taskId, model);
        var expectPage = "statuses/errors/404";
        var actualMessage = model.getAttribute("message");
        var expectedMessage = "Задание № " + taskId + " не  удалено";

        assertThat(actualPage).isEqualTo(expectPage);
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void whenGetEditTaskThenReturnEditPage() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        var priority = new Priority(1, "priority", 5);
        var category = new Category(1, "category");
        var task = new Task(1, "description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false,
                user, priority, Set.of(category));

        when(taskService.findTaskById(task.getId(), user.getTimeZone())).thenReturn(Optional.of(task));
        when(priorityService.findAllPriorityOrderById()).thenReturn(List.of(priority));
        when(categoryService.findAllCategoryOrderById()).thenReturn(List.of(category));

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("user", user);

        var madel = new ConcurrentModel();
        var actualPage = taskController.getEditTask(task.getId(), madel, httpSession);
        var expectedPage = "tasks/edit";
        var actualTask = madel.getAttribute("task");
        var actualPriorityList = madel.getAttribute("priorityList");
        var actualCategoryList = madel.getAttribute("categoryList");

        assertThat(actualPage).isEqualTo(expectedPage);
        assertThat(actualTask).usingRecursiveComparison().isEqualTo(task);
        assertThat(actualPriorityList).usingRecursiveComparison().isEqualTo(List.of(priority));
        assertThat(actualCategoryList).usingRecursiveComparison().isEqualTo(List.of(category));
    }

    @Test
    void whenGetEditTaskThenReturnErrorPageAndSameMessage() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        int taskId = 99;
        when(taskService.findTaskById(taskId, user.getTimeZone())).thenReturn(Optional.empty());

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("user", user);

        var madel = new ConcurrentModel();
        var actualPage = taskController.getEditTask(taskId, madel, httpSession);
        var expectedPage = "statuses/errors/404";
        var actualMessage = madel.getAttribute("message");
        var expectedMessage = "Задание № " + taskId + " не найдено";

        assertThat(actualPage).isEqualTo(expectedPage);
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void whenPostUpdateTaskThenReturnOneTaskPage() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        var priority = new Priority(1, "priority", 5);
        var category = new Category(1, "category");
        var task = new Task(1, "description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false,
                user, priority, Set.of(category));

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("user", user);

        when(taskService.update(task, Set.of(category.getId()), user.getTimeZone())).thenReturn(true);

        var model = new ConcurrentModel();
        var actualPage = taskController.postUpdateTask(task, Set.of(category.getId()), model, httpSession);
        var expectedPage = "redirect:/tasks/" + task.getId();

        assertThat(actualPage).isEqualTo(expectedPage);
    }

    @Test
    void whenPostUpdateTaskThenReturnErrorPageAndSameMessage() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        var priority = new Priority(1, "priority", 5);
        var category = new Category(1, "category");
        var task = new Task(1, "description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false,
                user, priority, Set.of(category));

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("user", user);

        when(taskService.update(task, Set.of(category.getId()), user.getTimeZone())).thenReturn(false);

        var model = new ConcurrentModel();
        var actualPage = taskController.postUpdateTask(task, Set.of(category.getId()), model, httpSession);
        var expectedPage = "statuses/errors/404";
        var actualMessage = model.getAttribute("message");
        var expectedMessage = "Задание № " + task.getId() + " не изменено";

        assertThat(actualPage).isEqualTo(expectedPage);
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void whenGetCreateTaskThenReturnCreatePage() {
        var priority = new Priority(1, "priority", 5);
        var category = new Category(1, "category");

        when(priorityService.findAllPriorityOrderById()).thenReturn(List.of(priority));
        when(categoryService.findAllCategoryOrderById()).thenReturn(List.of(category));

        var model = new ConcurrentModel();
        var actualPage = taskController.getCreateTask(model);
        var expectedPage = "tasks/create";

        var actualPriority = model.getAttribute("priorityList");
        var actualCategory = model.getAttribute("categoryList");

        assertThat(actualPage).isEqualTo(expectedPage);
        assertThat(actualPriority).isEqualTo(List.of(priority));
        assertThat(actualCategory).isEqualTo(List.of(category));
    }

    @Test
    void whenPostCreateTaskThenReturnAllTaskPage() {
        var user = new User(1, "name", "mail", "pass", "UTC");
        var priority = new Priority(1, "priority", 5);
        var category = new Category(1, "category");
        var task = new Task(1, "description",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false,
                user, priority, Set.of(category));

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("user", user);
        when(taskService.create(task, Set.of(category.getId()), priority.getId())).thenReturn(task);

        var actualPage = taskController.postCreateTask(task, Set.of(category.getId()), priority.getId(), httpSession);
        var expectPage = "redirect:/tasks/" + task.getId();

        assertThat(actualPage).isEqualTo(expectPage);
    }
}