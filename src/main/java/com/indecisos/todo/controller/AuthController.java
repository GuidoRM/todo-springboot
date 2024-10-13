package com.indecisos.todo.controller;

import com.indecisos.todo.dto.ApiResponse;
import com.indecisos.todo.dto.AuthenticationRequest;
import com.indecisos.todo.dto.AuthenticationResponse;
import com.indecisos.todo.model.User;
import com.indecisos.todo.service.JwtUserDetailsService;
import com.indecisos.todo.service.UserService;
import com.indecisos.todo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody User user) {
        try {
            // Asegúrate de que userWorkspaces esté inicializado
            user.setUserWorkspaces(new HashSet<>());

            // Establece otros campos necesarios
            user.setCreationDate(LocalDateTime.now());
            user.setIsAnonymous(false);  // o el valor que corresponda

            User savedUser = userService.save(user);

            ApiResponse<User> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "Usuario registrado con éxito",
                    savedUser
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(
                            HttpStatus.BAD_REQUEST.value(),
                            "Error al registrar el usuario: " + e.getMessage(),
                            null
                    ));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        long fixedUserId = userService.findByEmail(authenticationRequest.getEmail()).getId();
        final String jwt = jwtUtil.generateToken(userDetails.getUsername(), fixedUserId);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
