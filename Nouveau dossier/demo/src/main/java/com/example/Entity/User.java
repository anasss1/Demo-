package com.example.Entity;

@Table("users")
public class User {

    @Id
    private Long id;

    private String username;
    private String password;

    // Getters and Setters
}