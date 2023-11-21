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
    public String index(@RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "tasks_per_page", required = false) Integer tasksPerPage,
                        @ModelAttribute("newTask") Task task,
                        Model model) {
        if(page == null || tasksPerPage == null) {
            model.addAttribute("tasks", taskService.findAll());
        } else {
            model.addAttribute("tasks", taskService.findAllByPage(page, tasksPerPage));
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
