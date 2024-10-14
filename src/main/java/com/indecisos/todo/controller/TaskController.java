package com.indecisos.todo.controller;

import com.indecisos.todo.dto.ApiResponse;
import com.indecisos.todo.dto.ResponseDTO;
import com.indecisos.todo.model.Task;
import com.indecisos.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    @Autowired
    private TaskService taskService;

    private static final String UPLOAD_DIR = "uploads/"; // Directorio donde se guardarán las imágenes

    @PostMapping("/upload-image")
    public ResponseEntity<ResponseDTO> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Verificar que el directorio de subida existe, si no, crearlo
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Definir la ruta donde se guardará la imagen
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // Nombre único
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), filePath); // Copiar el archivo al directorio

            String imageUrl = "http://localhost:8080/api/tasks/" + UPLOAD_DIR + fileName; // URL para acceder a la imagen
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "Imagen subida con éxito!", imageUrl));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al subir la imagen", null));
        }
    }

    // ... El resto de tus métodos ...

    // Método para servir las imágenes
    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<byte[]> serveImage(@PathVariable String filename) {
        try {
            Path path = Paths.get(UPLOAD_DIR + filename);
            byte[] image = Files.readAllBytes(path);
            return ResponseEntity.ok().body(image);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
