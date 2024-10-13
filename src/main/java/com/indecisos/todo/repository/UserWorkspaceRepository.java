package com.indecisos.todo.repository;

import com.indecisos.todo.model.UserWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWorkspaceRepository extends JpaRepository<UserWorkspace, Long> {
}
