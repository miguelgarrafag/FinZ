package com.finz.rest.utils.entity;

import java.io.Serializable;

/**
 * @author SudTechnologies
 */
public class Param implements Serializable {

    private double dollarBuy;
    private double dollarSell;
    private double dispPerc;

    public Param() {
    }

    public Param(double dollarBuy, double dollarSell, double dispPerc) {
        this.dollarBuy = dollarBuy;
        this.dollarSell = dollarSell;
        this.dispPerc = dispPerc;
    }

    public double getDollarBuy() {
        return dollarBuy;
    }

    public void setDollarBuy(double dollarBuy) {
        this.dollarBuy = dollarBuy;
    }

    public double getDollarSell() {
        return dollarSell;
    }

    public void setDollarSell(double dollarSell) {
        this.dollarSell = dollarSell;
    }

    public double getDispPerc() {
        return dispPerc;
    }

    public void setDispPerc(double dispPerc) {
        this.dispPerc = dispPerc;
    }

    @Override
    public String toString() {
        return "Param{" +
                "dollarBuy=" + dollarBuy +
                ", dollarSell=" + dollarSell +
                ", dispPerc=" + dispPerc +
                '}';
    }
}
