package com.indecisos.todo.service;

import com.indecisos.todo.model.ListModel;
import com.indecisos.todo.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ListService {

    @Autowired
    private ListRepository listRepository;

    public List<ListModel> findAll() {
        return listRepository.findAll();
    }

    public Optional<ListModel> findById(Long id) { return listRepository.findById(id);}

    public List <ListModel> findByWorkspace(Long id_workspace) { return listRepository.findByWorkspace(id_workspace); }

    public ListModel save(ListModel listModel) { return listRepository.save(listModel); }

    public ListModel update(Long id, ListModel listModel) {
        Optional<ListModel> existingList = listRepository.findById(id);
        if (existingList.isPresent()) {
            ListModel updatedList = existingList.get();
            updatedList.setTitle(listModel.getTitle());
            updatedList.setDescription(listModel.getDescription());
            // Actualizar otros campos según sea necesario
            return listRepository.save(updatedList);
        } else {
            throw new RuntimeException("List not found with id " + id);
        }
    }

    public void deleteById(Long id) {
        listRepository.deleteById(id);
    }
}
