package com.indecisos.todo.repository;

import com.indecisos.todo.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ReminderRepository extends JpaRepository<Reminder, Long>  {

}
