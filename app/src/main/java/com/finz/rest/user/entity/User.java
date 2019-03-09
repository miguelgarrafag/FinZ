package com.finz.rest.user.entity;

import java.io.Serializable;

/**
 * @author SudTechnologies
 */
public class User implements Serializable {

    private long id;
    private String email;
    private String name;
    private String lastName;
    private String phone;
    private String dni;

    public User() {}

    public User(long id, String email, String name, String lastName, String phone, String dni) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.dni = dni;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }
}
