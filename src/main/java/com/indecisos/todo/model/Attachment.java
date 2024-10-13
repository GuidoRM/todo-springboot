package com.indecisos.todo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Attachment")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Attachment")
    private Long id;

    @Column(name = "File_Name")
    private String fileName;

    @Column(name = "URL")
    private String url;

    @Lob
    @Column(name = "Content")
    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "ID_Task", referencedColumnName = "ID_Task")
    private Task task;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
