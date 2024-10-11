package com.indecisos.todo.service;

import com.indecisos.todo.model.Reminder;
import com.indecisos.todo.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReminderService {

  @Autowired
  private ReminderRepository reminderRepository;

  public List<Reminder> findAll() {
    return reminderRepository.findAll();
  }

  public Optional<Reminder> findById(Long id) {
    return reminderRepository.findById(id);
  }

  public Reminder save(Reminder reminder) {
    return reminderRepository.save(reminder);
  }

  public void deleteById(Long id) {
    reminderRepository.deleteById(id);
  }
}
