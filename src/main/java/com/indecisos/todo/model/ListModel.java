package com.indecisos.todo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "List")
public class ListModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_List")
    private Long id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Last_Name")
    private String lastName;

    @Column(name = "Description")
    private String description;

    @Column(name = "ID_Workspace")
    private Long id_Workspace;


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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId_Workspace() {
        return id_Workspace;
    }

    public void setId_Workspace(Long id_Workspace) {
        this.id_Workspace = id_Workspace;
    }
}
