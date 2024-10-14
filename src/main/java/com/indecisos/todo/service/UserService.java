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

    public User updateUser(Long id, User userDetails) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Verificar si el correo ha cambiado y si el nuevo correo ya está en uso por otro usuario
            if (userDetails.getEmail() != null && !existingUser.getEmail().equals(userDetails.getEmail())) {
                Optional<User> userWithSameEmail = userRepository.findByEmail(userDetails.getEmail());
                if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getId().equals(id)) {
                    throw new IllegalArgumentException("El correo electrónico ya está registrado");
                }
                existingUser.setEmail(userDetails.getEmail());
            }

            // Actualizamos solo los campos que no son nulos
            if (userDetails.getFirstName() != null) {
                existingUser.setFirstName(userDetails.getFirstName());
            }
            if (userDetails.getLastName() != null) {
                existingUser.setLastName(userDetails.getLastName());
            }
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            }
            if (userDetails.getProfileImage() != null) {
                existingUser.setProfileImage(userDetails.getProfileImage());
            }

            return userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean isEmailUnique(String email, Long userId) {
        Optional<User> existingUserOptional = userRepository.findByEmail(email);

        // Si no hay un usuario con ese correo, o el usuario encontrado es el mismo que estamos actualizando
        return existingUserOptional
                .map(existingUser -> existingUser.getId().equals(userId))
                .orElse(true);
    }


    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
