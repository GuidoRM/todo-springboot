package com.indecisos.todo.controller;

import com.indecisos.todo.dto.ResponseDTO;
import com.indecisos.todo.model.Workspace;
import com.indecisos.todo.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
@CrossOrigin(origins = "http://localhost:5173")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllWorkspaces() {
        try {
            List<Workspace> workspaces = workspaceService.findAll();
            return ResponseEntity.ok(new ResponseDTO(200, "Solicitud satisfactoria!", workspaces));
        } catch (Exception e) {
            e.printStackTrace(); // <-- Agrega esto para ver el stack trace completo
            return ResponseEntity.status(500).body(new ResponseDTO(500, "Error al obtener workspaces", null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getWorkspaceById(@PathVariable Long id) {
        try {
            return workspaceService.findById(id)
                    .map(workspace -> ResponseEntity.ok(new ResponseDTO(200, "Solicitud satisfactoria!", workspace)))
                    .orElseGet(() -> ResponseEntity.status(404).body(new ResponseDTO(404, "Workspace no encontrado", null)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseDTO(500, "Error al obtener el workspace", null));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDTO> getWorkspaceByUserId(@PathVariable Long userId) {
        try {
            List<Workspace> workspaces = workspaceService.findByUserId(userId);
            if (workspaces.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(HttpStatus.NOT_FOUND.value(), "No se encontraron workspaces para el usuario", null));
            }
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "Solicitud satisfactoria!", workspaces));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener workspaces del usuario", null));
        }
    }


    @PostMapping
    public ResponseEntity<ResponseDTO> createWorkspace(@RequestBody Workspace workspace) {
        try {
            Workspace savedWorkspace = workspaceService.save(workspace);
            return ResponseEntity.ok(new ResponseDTO(200, "Workspace creado con éxito!", savedWorkspace));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseDTO(500, "Error al crear el workspace", null));
        }
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ResponseDTO> createWorkspaceForUser(@PathVariable Long userId, @RequestBody Workspace workspace) {
        try {
            Workspace savedWorkspace = workspaceService.createWorkspaceForUser(userId, workspace);
            return ResponseEntity.ok(new ResponseDTO(200, "Workspace creado con éxito!", savedWorkspace));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseDTO(500, "Error al crear el workspace", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateWorkspace(@PathVariable Long id, @RequestBody Workspace workspaceDetails) {
        try {
            Workspace updatedWorkspace = workspaceService.updateWorkspace(id, workspaceDetails);
            return ResponseEntity.ok(new ResponseDTO(200, "Workspace actualizado con éxito!", updatedWorkspace));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new ResponseDTO(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseDTO(500, "Error al actualizar el workspace", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteWorkspace(@PathVariable Long id) {
        try {
            workspaceService.deleteById(id);
            return ResponseEntity.ok(new ResponseDTO(200, "Workspace eliminado con éxito!", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseDTO(500, "Error al eliminar el workspace", null));
        }
    }
}
