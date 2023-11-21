package com.maximyasn.todolist.service;

import com.maximyasn.todolist.dao.TaskDao;
import com.maximyasn.todolist.domain.Status;
import com.maximyasn.todolist.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskDao taskDao;

    @Autowired
    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void save(Task task) {
        taskDao.save(task);
    }

    @Transactional
    public void updateTask(Task task) {
        taskDao.update(task);
    }

    public void deleteTaskById(Integer id) {
        taskDao.delete(id);
    }

    public Task findOne(Integer id) {
        return taskDao.findOne(id);
    }

    public List<Task> findAll() {
        return taskDao.findAll();
    }

    public List<Task> findAllByPage(Integer page, Integer tasksPerPage) {
        return taskDao.findAllByPage(page, tasksPerPage);
    }
}
