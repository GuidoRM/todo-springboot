package com.indecisos.todo.service;

import com.indecisos.todo.model.User;
import com.indecisos.todo.model.UserWorkspace;
import com.indecisos.todo.model.Workspace;
import com.indecisos.todo.repository.UserRepository;
import com.indecisos.todo.repository.UserWorkspaceRepository;
import com.indecisos.todo.repository.WorkspaceRepository;
import com.indecisos.todo.type.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceService {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserWorkspaceRepository userWorkspaceRepository;

    public List<Workspace> findAll() {
        return workspaceRepository.findAll();
    }

    public Optional<Workspace> findById(Long id) {
        return workspaceRepository.findById(id);
    }

    public Workspace save(Workspace workspace) {
        return workspaceRepository.save(workspace);
    }

    public Workspace createWorkspaceForUser(Long userId, Workspace workspace) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        User user = userOptional.get();
        workspace.setCreationDate(LocalDateTime.now());
        Workspace savedWorkspace = workspaceRepository.save(workspace);

        // Crear y guardar la relación en la tabla intermedia UserWorkspace
        UserWorkspace userWorkspace = new UserWorkspace();
        userWorkspace.setUser(user);
        userWorkspace.setWorkspace(savedWorkspace);
        userWorkspace.setRole(UserRole.owner);  // Asignar el rol de "propietario" al usuario que crea el workspace

        userWorkspaceRepository.save(userWorkspace);

        return savedWorkspace;
    }

    public void deleteById(Long id) {
        workspaceRepository.deleteById(id);
    }

    public List<Workspace> findByUserId(Long userId) {
        return workspaceRepository.findByUserId(userId);
    }

    public Workspace updateWorkspace(Long id, Workspace workspaceDetails) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (optionalWorkspace.isPresent()) {
            Workspace workspace = optionalWorkspace.get();
            workspace.setNameWorkspace(workspaceDetails.getNameWorkspace());
            workspace.setDescriptionWorkspace(workspaceDetails.getDescriptionWorkspace());
            workspace.setTypeWorkspace(workspaceDetails.getTypeWorkspace());
            workspace.setAccessCode(workspaceDetails.getAccessCode());
            return workspaceRepository.save(workspace);
        } else {
            throw new RuntimeException("Workspace not found with id " + id);
        }
    }
}
