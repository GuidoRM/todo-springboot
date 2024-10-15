package com.indecisos.todo.controller;

import com.indecisos.todo.model.Attachment;
import com.indecisos.todo.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attachments")
@CrossOrigin(origins = "http://localhost:5173")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    // Obtener todos los adjuntos
    @GetMapping
    public ResponseEntity<List<Attachment>> getAllAttachments() {
        return ResponseEntity.ok(attachmentService.findAll());
    }

    // Obtener adjunto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Attachment> getAttachmentById(@PathVariable Long id) {
        Optional<Attachment> attachment = attachmentService.findById(id);
        return attachment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener adjuntos por tarea
    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Attachment>> getAttachmentsByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok(attachmentService.findByTaskId(taskId));
    }

    // Crear un nuevo adjunto y vincularlo con una tarea
    @PostMapping("/task/{taskId}")
    public ResponseEntity<Attachment> createAttachment(@RequestBody Attachment attachment, @PathVariable Long taskId) {
        return ResponseEntity.ok(attachmentService.save(attachment, taskId));
    }

    // Actualizar un adjunto existente
    @PutMapping("/{id}")
    public ResponseEntity<Attachment> updateAttachment(@PathVariable Long id, @RequestBody Attachment attachmentDetails) {
        return ResponseEntity.ok(attachmentService.update(id, attachmentDetails));
    }

    // Eliminar un adjunto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        attachmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
