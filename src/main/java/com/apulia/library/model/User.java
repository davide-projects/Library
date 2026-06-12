package com.apulia.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonPropertyOrder({"id", "username", "role"})
@JsonIgnoreProperties(ignoreUnknown = false)

@Entity
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Il campo 'username' è obbligatorio")
    @Size(min = 3, max = 50,
            message = "Username must be between 3 and 50 characters")
    @Column(nullable = false, unique = true, name = "username")
    private String username;

    @NotBlank(message = "Il campo 'password' è obbligatorio")
    @Size(min = 8, max = 255,
            message = "Password must be between 8 and 255 characters")
    @Column(nullable = false, name = "password")
    private String password;

    @NotBlank(message = "Il campo 'role' è obbligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    private Role role;

    public User() {}

    public User(String username, String password, Role role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
