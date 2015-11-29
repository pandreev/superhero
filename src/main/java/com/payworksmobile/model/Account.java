package com.payworksmobile.model;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Account {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Deprecated //hibernate
    public Account(){}

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
