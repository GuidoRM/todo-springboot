package com.indecisos.todo.model;

import com.indecisos.todo.type.WorkspaceType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Workspace")
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Workspace")
    private Long idWorkspace;

    @Column(name = "Name_Workspace")
    private String nameWorkspace;

    @Column(name = "Description_Workspace")
    private String descriptionWorkspace;

    @Column(name = "Type_Workspace")
    @Enumerated(EnumType.STRING)
    private WorkspaceType typeWorkspace; // Enum public/private

    @Column(name = "Access_Code")
    private String accessCode;

    @Column(name = "Creation_Date")
    private LocalDateTime creationDate;

    // Getters y Setters

    public Long getIdWorkspace() {
        return idWorkspace;
    }

    public void setIdWorkspace(Long idWorkspace) {
        this.idWorkspace = idWorkspace;
    }

    public String getNameWorkspace() {
        return nameWorkspace;
    }

    public void setNameWorkspace(String nameWorkspace) {
        this.nameWorkspace = nameWorkspace;
    }

    public String getDescriptionWorkspace() {
        return descriptionWorkspace;
    }

    public void setDescriptionWorkspace(String descriptionWorkspace) {
        this.descriptionWorkspace = descriptionWorkspace;
    }

    public WorkspaceType getTypeWorkspace() {
        return typeWorkspace;
    }

    public void setTypeWorkspace(WorkspaceType typeWorkspace) {
        this.typeWorkspace = typeWorkspace;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
