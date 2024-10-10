package com.indecisos.todo.controller;

import com.indecisos.todo.dto.ApiResponse;
import com.indecisos.todo.model.ListModel;
import com.indecisos.todo.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lists")
public class ListController {

    @Autowired
    private ListService listService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ListModel>>> getAllLists() {
        try {
            List<ListModel> lists = listService.findAll();
            ApiResponse<List<ListModel>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Solicitud satisfactoria!",
                    lists
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener las listas", null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getListById(@PathVariable Long id) {
        try {
            return listService.findById(id)
                    .map(list -> {
                        ApiResponse<Object> response = new ApiResponse<>(
                                HttpStatus.OK.value(),
                                "Solicitud satisfactoria!",
                                list
                        );
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        ApiResponse<Object> response = new ApiResponse<>(
                                HttpStatus.NOT_FOUND.value(),
                                "List not found",
                                null
                        );
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener la lista", null));
        }
    }

    @GetMapping("/workspace/{id_workspace}")
    public ResponseEntity<ApiResponse<List<ListModel>>> getListsByWorkspace(@PathVariable Long id_workspace) {
        try {
            List<ListModel> lists = listService.findByWorkspace(id_workspace);
            ApiResponse<List<ListModel>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Solicitud satisfactoria!",
                    lists
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener listas del workspace", null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ListModel>> createList(@RequestBody ListModel listModel) {
        try {
            ListModel savedList = listService.save(listModel);
            ApiResponse<ListModel> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "List creada con éxito!",
                    savedList
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al crear la lista", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteList(@PathVariable Long id) {
        try {
            listService.deleteById(id);
            ApiResponse<Object> response = new ApiResponse<>(
                    HttpStatus.NO_CONTENT.value(),
                    "List eliminada con éxito!",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al eliminar la lista", null));
        }
    }
}
