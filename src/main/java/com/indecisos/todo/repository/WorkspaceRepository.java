package com.indecisos.todo.repository;

import com.indecisos.todo.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    @Query("SELECT w FROM Workspace w JOIN UserWorkspace uw ON w.id = uw.workspace.id WHERE uw.user.id = :userId")
    List<Workspace> findByUserId(@Param("userId") Long userId);
}

