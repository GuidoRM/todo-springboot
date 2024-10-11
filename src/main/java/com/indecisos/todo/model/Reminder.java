package com.indecisos.todo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reminder")
public class Reminder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_Reminder")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "ID_Task", nullable = false)
  private Task task;

  @Column(name = "Start_Date", nullable = false)
  private LocalDateTime startDate;

  @Column(name = "End_Date", nullable = false)
  private LocalDateTime endDate;

  @Column(name = "Is_Reminded", nullable = false)
  private boolean isReminded;

  // Getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public boolean isReminded() {
    return isReminded;
  }

  public void setReminded(boolean reminded) {
    isReminded = reminded;
  }
}
