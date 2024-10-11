package com.indecisos.todo.controller;

import com.indecisos.todo.model.Reminder;
import com.indecisos.todo.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reminders")
public class ReminderController {

  @Autowired
  private ReminderService reminderService;

  @GetMapping
  public List<Reminder> getAllReminders() {
    return reminderService.findAll();
  }

  @GetMapping("/{id}")
  public Optional<Reminder> getReminderById(@PathVariable Long id) {
    return reminderService.findById(id);
  }

  @PostMapping
  public Reminder createReminder(@RequestBody Reminder reminder) {
    return reminderService.save(reminder);
  }

  @DeleteMapping("/{id}")
  public void deleteReminder(@PathVariable Long id) {
    reminderService.deleteById(id);
  }

}
