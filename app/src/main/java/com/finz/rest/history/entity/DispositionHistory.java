package com.finz.rest.history.entity;

import java.io.Serializable;

public class DispositionHistory implements Serializable {

   private String creationDate;
   private String statusText;
   private String lastFour;
   private String nroDestiny;
   private double amount;
   private double amountTotal;

    public DispositionHistory() {
    }

    public DispositionHistory(String creationDate, String statusText, String lastFour, String nroDestiny, double amount, double amountTotal) {
        this.creationDate = creationDate;
        this.statusText = statusText;
        this.lastFour = lastFour;
        this.nroDestiny = nroDestiny;
        this.amount = amount;
        this.amountTotal = amountTotal;
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

    public String getLastFour() {
        return lastFour;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }

    public String getNroDestiny() {
        return nroDestiny;
    }

    public void setNroDestiny(String nroDestiny) {
        this.nroDestiny = nroDestiny;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(double amountTotal) {
        this.amountTotal = amountTotal;
    }

    @Override
    public String toString() {
        return "DispositionHistory{" +
                "creationDate='" + creationDate + '\'' +
                ", statusText='" + statusText + '\'' +
                ", lastFour='" + lastFour + '\'' +
                ", nroDestiny='" + nroDestiny + '\'' +
                ", amount=" + amount +
                ", amountTotal=" + amountTotal +
                '}';
    }
}
