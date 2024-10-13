package com.indecisos.todo.controller;

import com.indecisos.todo.dto.ApiResponse;
import com.indecisos.todo.model.Label;
import com.indecisos.todo.model.Task;
import com.indecisos.todo.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.indecisos.todo.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/labels")
@CrossOrigin(origins = "http://localhost:5173")
public class LabelController {

    @Autowired
    private LabelService labelService;
    @Autowired
    private TaskService taskService;


    @GetMapping
    public ResponseEntity<ApiResponse<List<Label>>> getAllLabels() {
        try {
            List<Label> labels = labelService.findAll();
            ApiResponse<List<Label>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Solicitud satisfactoria!",
                    labels
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener las etiquetas", null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getLabelById(@PathVariable Long id) {
        try {
            return labelService.findById(id)
                    .map(label -> {
                        ApiResponse<Object> response = new ApiResponse<>(
                                HttpStatus.OK.value(),
                                "Solicitud satisfactoria!",
                                label
                        );
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        ApiResponse<Object> response = new ApiResponse<>(
                                HttpStatus.NOT_FOUND.value(),
                                "Etiqueta no encontrada",
                                null
                        );
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener la etiqueta", null));
        }
    }
    // Nuevo endpoint para obtener todas las etiquetas relacionadas con una tarea
    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<ApiResponse<List<Label>>> getLabelsByTaskId(@PathVariable Long taskId) {
        try {
            Task task = taskService.findById(taskId)
                    .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id " + taskId));

            List<Label> labels = List.copyOf(task.getLabels());
            ApiResponse<List<Label>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Etiquetas obtenidas con éxito!",
                    labels
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener las etiquetas de la tarea", null));
        }
    }
    @PostMapping
    public ResponseEntity<ApiResponse<Label>> createLabel(@RequestBody Label label) {
        try {
            Label savedLabel = labelService.save(label);
            ApiResponse<Label> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "Etiqueta creada con éxito!",
                    savedLabel
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al crear la etiqueta", null));
        }
    }

    // Nuevo endpoint para vincular una etiqueta a una tarea
    @PostMapping("/{labelId}/tasks/{taskId}")
    public ResponseEntity<ApiResponse<Object>> addLabelToTask(@PathVariable Long labelId, @PathVariable Long taskId) {
        try {
            Task task = taskService.findById(taskId)
                    .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id " + taskId));

            Label label = labelService.findById(labelId)
                    .orElseThrow(() -> new RuntimeException("Etiqueta no encontrada con id " + labelId));

            task.getLabels().add(label);
            taskService.save(task);

            ApiResponse<Object> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Etiqueta vinculada a la tarea con éxito!",
                    null
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al vincular la etiqueta a la tarea", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Label>> updateLabel(@PathVariable Long id, @RequestBody Label labelDetails) {
        try {
            Label updatedLabel = labelService.update(id, labelDetails);
            ApiResponse<Label> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Etiqueta actualizada con éxito!",
                    updatedLabel
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al actualizar la etiqueta", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteLabel(@PathVariable Long id) {
        try {
            labelService.deleteById(id);
            ApiResponse<Object> response = new ApiResponse<>(
                    HttpStatus.NO_CONTENT.value(),
                    "Etiqueta eliminada con éxito!",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al eliminar la etiqueta", null));
        }
    }
}
