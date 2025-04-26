package com.example.auth_server.model;

public class AuthCredential {
    public String username;
    public String role;

    public AuthCredential(String username,String role) {
        this.username=username;
        this.role=role;
    }

    public String getUsername() {
        return username;
    }
    public String getRole() {
        return role;
    }
    public void setUsername(String username) {
        this.username=username;
    }

    public void setRole(String role) {
        this.role=role;
    }

}
