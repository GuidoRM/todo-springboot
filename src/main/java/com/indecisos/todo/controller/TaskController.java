package com.indecisos.todo.controller;

import com.indecisos.todo.model.ListModel;
import com.indecisos.todo.model.Task;
import com.indecisos.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.findById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Task> getAllTaskOfWorkspace(@PathVariable Long id_workspace) {
        return taskService.findAllTaskOfWorkspace(id_workspace);
    }

    @GetMapping
    public List<Task> getTaskByList (@PathVariable Long id_list) {
        return taskService.findByList(id_list);
    }

    @GetMapping
    public List<Task> findByStatus (@PathVariable String status, @PathVariable Long id_workspace) {
        return taskService.findByStatus(status, id_workspace);
    }

    @GetMapping
    public List<Task> findByDueDate (@PathVariable LocalDateTime due_date, @PathVariable Long id_workspace) {
        return taskService.findByDueDate(due_date, id_workspace);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        try {
            Task savedTask = taskService.save(task);
            return ResponseEntity.ok(savedTask);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
