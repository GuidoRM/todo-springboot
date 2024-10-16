package com.indecisos.todo.service;

import com.indecisos.todo.model.Task;
import com.indecisos.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> findAllTaskOfWorkspace(Long id_workspace) {
        return taskRepository.findAllTaskOfWorkspace(id_workspace);
    }

    public List<Task> findByList(Long id_list) {
        return taskRepository.findByList(id_list);
    }

    public List<Task> findByStatus(String status, Long id_workspace) {
        return taskRepository.findByStatus(status, id_workspace);
    }

    public List<Task> findByDueDate(LocalDateTime due_date, Long id_workspace) {
        return taskRepository.findByDueDate(due_date, id_workspace);
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    public Task update(Long id, Task taskDetails) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent()) {
            Task updatedTask = existingTask.get();
            updatedTask.setTitle(taskDetails.getTitle());
            updatedTask.setDescription(taskDetails.getDescription());
            updatedTask.setPriority(taskDetails.getPriority());
            updatedTask.setStatus(taskDetails.getStatus());
            updatedTask.setDue_Date(taskDetails.getDue_Date());
            // Actualizar etiquetas y adjuntos si es necesario
            return taskRepository.save(updatedTask);
        } else {
            throw new RuntimeException("Task not found with id " + id);
        }
    }
}
