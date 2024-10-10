package com.indecisos.todo.controller;

import com.indecisos.todo.model.ListModel;
import com.indecisos.todo.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lists")
public class ListController {

    @Autowired
    private ListService listService;

    @GetMapping
    public List<ListModel> getAllLists() {
        return listService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListModel> getListById(@PathVariable Long id) {
        return listService.findById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ListModel> getListsByWorkspace(@PathVariable Long id_workspace) {
        return listService.findByWorkspace(id_workspace);
    }

    @PostMapping
    public ResponseEntity<ListModel> createList(@RequestBody ListModel listModel) {
        try {
            ListModel savedList = listService.save(listModel);
            return ResponseEntity.ok(savedList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteList(@PathVariable Long id) {
        listService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
