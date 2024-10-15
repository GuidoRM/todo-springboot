package com.indecisos.todo.controller;

import com.indecisos.todo.dto.ApiResponse;
import com.indecisos.todo.model.User;
import com.indecisos.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.findAll();
        ApiResponse<List<User>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Solicitud satisfactoria!",
                users
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> {
                    ApiResponse<Object> response = new ApiResponse<>(
                            HttpStatus.OK.value(),
                            "Solicitud satisfactoria!",
                            user
                    );
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    ApiResponse<Object> response = new ApiResponse<>(
                            HttpStatus.NOT_FOUND.value(),
                            "User not found",
                            null
                    );
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                });
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @PathVariable Long id,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {

        try {
            User userDetails = new User();
            userDetails.setFirstName(firstName);
            userDetails.setLastName(lastName);
            userDetails.setEmail(email);
            userDetails.setPassword(password);

            if (profileImage != null && !profileImage.isEmpty()) {
                userDetails.setProfileImage(profileImage.getBytes());
            }

            User updatedUser = userService.updateUser(id, userDetails);
            ApiResponse<User> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Perfil actualizado correctamente",
                    updatedUser
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<User> response = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>(new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al procesar la imagen de perfil",
                    null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        ApiResponse<User> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Solicitud satisfactoria!",
                savedUser
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.NO_CONTENT.value(),
                "User deleted successfully",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
