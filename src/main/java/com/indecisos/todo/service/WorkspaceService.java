package com.indecisos.todo.service;

import com.indecisos.todo.model.Workspace;
import com.indecisos.todo.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceService {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    public List<Workspace> findAll() {
        return workspaceRepository.findAll();
    }

    public Optional<Workspace> findById(Long id) {
        return workspaceRepository.findById(id);
    }

    public Workspace save(Workspace workspace) {
        return workspaceRepository.save(workspace);
    }

    public void deleteById(Long id) {
        workspaceRepository.deleteById(id);
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
