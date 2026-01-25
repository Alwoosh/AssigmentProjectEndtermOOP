package edu.aitu.oop3.models;

import java.time.LocalDate;

public class Member {
    private int id;
    private String name;
    private String email;
    private LocalDate expiryDate;

    public Member(int id, String name, String email, LocalDate expiryDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.expiryDate = expiryDate;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDate getExpiryDate() { return expiryDate; }

    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
}
