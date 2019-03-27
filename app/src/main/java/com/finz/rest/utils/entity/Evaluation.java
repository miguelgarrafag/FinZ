package com.finz.rest.utils.entity;

import java.io.Serializable;

public class Evaluation implements Serializable {

    private long id;
    private String codOperation;

    public Evaluation(long id, String codOperation) {
        this.id = id;
        this.codOperation = codOperation;
    }

    public Evaluation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodOperation() {
        return codOperation;
    }

    public void setCodOperation(String codOperation) {
        this.codOperation = codOperation;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "id=" + id +
                ", codOperation='" + codOperation + '\'' +
                '}';
    }
}
