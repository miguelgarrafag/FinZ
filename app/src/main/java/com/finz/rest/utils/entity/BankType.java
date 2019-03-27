package com.finz.rest.utils.entity;

import java.io.Serializable;

/**
 * @author SudTechnologies
 */
public class BankType implements Serializable {

    private long id;
    private String name;

    public BankType() {
    }

    public BankType(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BankType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
