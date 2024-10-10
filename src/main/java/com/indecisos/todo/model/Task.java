package com.indecisos.todo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Task")
    private Long id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "Priority")
    private int priority;

    @Column(name = "Status")
    private String status;

    @Column(name = "Due_Date")
    private LocalDateTime due_Date;

    @Column(name = "ID_List")
    private Long id_List;


    // Getters y setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDue_Date() {
        return due_Date;
    }

    public void setDue_Date(LocalDateTime due_Date) {
        this.due_Date = due_Date;
    }

    public Long getId_List() {
        return id_List;
    }

    public void setId_List(Long id_List) {
        this.id_List = id_List;
    }
}
