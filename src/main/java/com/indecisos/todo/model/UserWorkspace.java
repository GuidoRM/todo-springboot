package com.indecisos.todo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.indecisos.todo.type.UserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "User_Workspace")
public class UserWorkspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_User_Workspace")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_User", referencedColumnName = "ID_User")
    @JsonIgnore  // Agrega esta anotación para evitar referencias circulares
    private User user;

    @ManyToOne
    @JoinColumn(name = "ID_Workspace", referencedColumnName = "ID_Workspace")
    @JsonBackReference
    @JsonIgnoreProperties("userWorkspaces")
    @JsonIgnore  // Agrega esta anotación para evitar referencias circulares
    private Workspace workspace;

    @Column(name = "Role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
