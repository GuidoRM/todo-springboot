package com.indecisos.todo.repository;

import com.indecisos.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t " +
            "JOIN ListModel l ON t.id_List = l.id " +
            "JOIN Workspace w ON l.id_Workspace = w.idWorkspace " +
            "WHERE w.idWorkspace = :id_workspace")
    List<Task> findAllTaskOfWorkspace(@Param("id_workspace") Long id_workspace);

    @Query("SELECT t FROM Task t WHERE t.id_List = :id_list")
    List<Task> findByList(@Param("id_list") Long id_list);

    @Query("SELECT t FROM Task t " +
            "JOIN ListModel l ON t.id_List = l.id " +
            "JOIN Workspace w ON l.id_Workspace = w.idWorkspace " +
            "WHERE t.status = :status " +
            "AND w.idWorkspace = :id_workspace")
    List<Task> findByStatus(@Param("status") String status, @Param("id_workspace") Long id_workspace);

    @Query("SELECT t FROM Task t " +
            "JOIN ListModel l ON t.id_List = l.id " +
            "JOIN Workspace w ON l.id_Workspace = w.idWorkspace " +
            "WHERE t.due_Date = :due_date " +
            "AND w.idWorkspace = :id_workspace " +
            "ORDER BY t.due_Date ASC")
    List<Task> findByDueDate(@Param("due_date") LocalDateTime due_date, @Param("id_workspace") Long id_workspace);
}
