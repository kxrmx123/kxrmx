package com.example.recipehub.model;

public class User {

    // success login
    private int id;
    private String email;
    private String username;
    private String password;
    private String token;
    private String lease;
    private String role_name;
    private String role_permission;
    private int is_active;
    private String secret;

    public User() {
    }

    public User(int id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", lease='" + lease + '\'' +
                ", role_name='" + role_name + '\'' +
                ", role_permission='" + role_permission + '\'' +
                ", is_active=" + is_active +
                ", secret='" + secret + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLease() {
        return lease;
    }

    public void setLease(String lease) {
        this.lease = lease;
    }

    public String getRole() {
        return role_name;
    }

    public void setRole(String role_name) {
        this.role_name = role_name;
    }

    public String getPermission() {
        return role_permission;
    }

    public void setPermission(String role_permission) {
        this.role_permission = role_permission;
    }



    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }


}
