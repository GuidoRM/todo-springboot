package com.indecisos.todo.service;

import com.indecisos.todo.model.Label;
import com.indecisos.todo.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    public List<Label> findAll() {
        return labelRepository.findAll();
    }

    public Optional<Label> findById(Long id) {
        return labelRepository.findById(id);
    }

    public Label save(Label label) {
        return labelRepository.save(label);
    }

    public void deleteById(Long id) {
        labelRepository.deleteById(id);
    }

    public Label update(Long id, Label labelDetails) {
        Optional<Label> existingLabel = labelRepository.findById(id);
        if (existingLabel.isPresent()) {
            Label updatedLabel = existingLabel.get();
            updatedLabel.setTitle(labelDetails.getTitle());
            updatedLabel.setColorHex(labelDetails.getColorHex());
            // Actualizar otros campos según sea necesario
            return labelRepository.save(updatedLabel);
        } else {
            throw new RuntimeException("Etiqueta no encontrada con id " + id);
        }
    }
}
