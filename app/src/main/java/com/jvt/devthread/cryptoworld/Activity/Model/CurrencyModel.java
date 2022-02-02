package com.jvt.devthread.cryptoworld.Activity.Model;

public class CurrencyModel {
    String coinId, name, symbol,dominance;
    double price;

    public CurrencyModel() {
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDominance() {
        return dominance;
    }

    public void setDominance(String dominance) {
        this.dominance = dominance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CurrencyModel(String coinId, String name, String symbol, String dominance, double price) {
        this.coinId = coinId;
        this.name = name;
        this.symbol = symbol;
        this.dominance = dominance;
        this.price = price;
    }
}
