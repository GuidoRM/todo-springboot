package com.indecisos.todo.repository;

import com.indecisos.todo.model.ListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListRepository extends JpaRepository<ListModel, Long> {
    @Query("SELECT l FROM List l WHERE l.id_Workspace = :id")
    List<ListModel> findByWorkspace(@Param("id") Long id);
}
