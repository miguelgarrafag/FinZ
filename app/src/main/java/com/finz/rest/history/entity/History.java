package com.finz.rest.history.entity;

import java.io.Serializable;

public class History implements Serializable {

    private long id;
    private String token;

    public History(long id, String token) {
        this.id = id;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "FirebaseToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                '}';
    }
}
