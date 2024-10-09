package com.indecisos.todo.service;

import com.indecisos.todo.model.User;
import com.indecisos.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyección del PasswordEncoder

    public User save(User user) {
        // Verificar si el email ya existe
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }

        // Configurar valores predeterminados
        if (user.getTokenExpiration() == null) {
            user.setTokenExpiration(LocalDateTime.now().plusHours(1)); // Expira en 1 hora
        }
        if (user.getIsAnonymous() == null) {
            user.setIsAnonymous(false); // No es anónimo por defecto
        }

        // Codificar la contraseña antes de guardarla
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }



    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
