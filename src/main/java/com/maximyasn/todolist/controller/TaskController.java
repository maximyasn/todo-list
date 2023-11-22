package com.maximyasn.todolist.controller;

import com.maximyasn.todolist.domain.Status;
import com.maximyasn.todolist.domain.Task;
import com.maximyasn.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ModelAttribute("statusList")
    public List<Status> statusList() {
        return Arrays.asList(Status.values());
    }

    @GetMapping
    public String index(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                        @RequestParam(value = "tasks_per_page", required = false, defaultValue = "10") Integer tasksPerPage,
                        @ModelAttribute("newTask") Task task,
                        Model model) {

        List<Task> all = taskService.findAllByPage((page - 1) * tasksPerPage, tasksPerPage);
        model.addAttribute("tasks", all);

        int totalPages = (int) Math.ceil(1.0 * taskService.getAllCount() / tasksPerPage);
        if(totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "index";
    }


    @PostMapping("/new")
    public String addNewTask(Task task) {
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Task task = taskService.findOne(id);
        System.out.println(task);
        model.addAttribute("task", task);
        return "edit";
    }

    @PatchMapping("/{id}/edit")
    public String editTask(@ModelAttribute("task") Task task,
                           @PathVariable("id") Integer id) {
        task.setId(id);
        taskService.updateTask(task);
        return "redirect:/tasks";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable("id") Integer id) {
        taskService.deleteTaskById(id);
        return "redirect:/tasks";
    }
}
