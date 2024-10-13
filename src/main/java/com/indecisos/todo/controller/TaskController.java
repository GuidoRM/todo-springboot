package com.indecisos.todo.controller;

import com.indecisos.todo.dto.ApiResponse;
import com.indecisos.todo.dto.ResponseDTO;
import com.indecisos.todo.model.Task;
import com.indecisos.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllTasks() {
        try {
            List<Task> tasks = taskService.findAll();
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.value(),
                    "Solicitud satisfactoria!",
                    tasks
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener tareas", null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getTaskById(@PathVariable Long id) {
        try {
            return taskService.findById(id)
                    .map(task -> {
                        ResponseDTO response = new ResponseDTO(
                                HttpStatus.OK.value(),
                                "Solicitud satisfactoria!",
                                task
                        );
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        ResponseDTO response = new ResponseDTO(
                                HttpStatus.NOT_FOUND.value(),
                                "Task not found",
                                null
                        );
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener la tarea", null));
        }
    }

    @GetMapping("/workspace/{id_workspace}")
    public ResponseEntity<ResponseDTO> getAllTaskOfWorkspace(@PathVariable Long id_workspace) {
        try {
            List<Task> tasks = taskService.findAllTaskOfWorkspace(id_workspace);
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.value(),
                    "Solicitud satisfactoria!",
                    tasks
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener tareas del workspace", null));
        }
    }

    @GetMapping("/list/{id_list}")
    public ResponseEntity<ResponseDTO> getTaskByList(@PathVariable Long id_list) {
        try {
            List<Task> tasks = taskService.findByList(id_list);
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.value(),
                    "Solicitud satisfactoria!",
                    tasks
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener tareas de la lista", null));
        }
    }

    @GetMapping("/status/{status}/workspace/{id_workspace}")
    public ResponseEntity<ResponseDTO> findByStatus(@PathVariable String status, @PathVariable Long id_workspace) {
        try {
            List<Task> tasks = taskService.findByStatus(status, id_workspace);
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.value(),
                    "Solicitud satisfactoria!",
                    tasks
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener tareas por estado", null));
        }
    }

    @GetMapping("/due-date/{due_date}/workspace/{id_workspace}")
    public ResponseEntity<ResponseDTO> findByDueDate(@PathVariable LocalDateTime due_date, @PathVariable Long id_workspace) {
        try {
            List<Task> tasks = taskService.findByDueDate(due_date, id_workspace);
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.value(),
                    "Solicitud satisfactoria!",
                    tasks
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener tareas por fecha de vencimiento", null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createTask(@RequestBody Task task) {
        try {
            Task savedTask = taskService.save(task);
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.CREATED.value(),
                    "Task creado con éxito!",
                    savedTask
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al crear la tarea", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteById(id);
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.NO_CONTENT.value(),
                    "Task eliminado con éxito!",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al eliminar la tarea", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        try {
            Optional<Task> optionalTask = taskService.findById(id);
            if (optionalTask.isPresent()) {
                Task task = optionalTask.get();
                task.setTitle(taskDetails.getTitle());
                task.setDescription(taskDetails.getDescription());
                task.setPriority(taskDetails.getPriority());
                task.setStatus(taskDetails.getStatus());
                task.setDue_Date(taskDetails.getDue_Date());
                task.setId_List(taskDetails.getId_List());
                Task updatedTask = taskService.save(task);
                ResponseDTO response = new ResponseDTO(
                        HttpStatus.OK.value(),
                        "Task actualizado con éxito!",
                        updatedTask
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ResponseDTO response = new ResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        "Task not found",
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al actualizar la tarea", null));
        }
    }
}
