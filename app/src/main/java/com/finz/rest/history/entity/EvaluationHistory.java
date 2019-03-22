package com.finz.rest.history.entity;

import java.io.Serializable;

public class EvaluationHistory implements Serializable {

   private String creationDate;
   private String statusText;
   private String type;
   private double amount;

    public EvaluationHistory() {
    }

    public EvaluationHistory(String creationDate, String statusText, String type, double amount) {
        this.creationDate = creationDate;
        this.statusText = statusText;
        this.type = type;
        this.amount = amount;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "EvaluationHistory{" +
                "creationDate='" + creationDate + '\'' +
                ", statusText='" + statusText + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                '}';
    }
}
