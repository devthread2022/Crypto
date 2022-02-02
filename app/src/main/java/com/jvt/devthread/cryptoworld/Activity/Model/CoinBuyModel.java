package com.jvt.devthread.cryptoworld.Activity.Model;

public class CoinBuyModel {
    String coinId, name, symbol,orderType, date, orderId,unit;
    double price,purchasedAmount;

    public CoinBuyModel() {
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPurchasedAmount() {
        return purchasedAmount;
    }

    public void setPurchasedAmount(double purchasedAmount) {
        this.purchasedAmount = purchasedAmount;
    }

    public CoinBuyModel(String coinId, String name, String symbol, String orderType, String date, String orderId, String unit, double price, double purchasedAmount) {
        this.coinId = coinId;
        this.name = name;
        this.symbol = symbol;
        this.orderType = orderType;
        this.date = date;
        this.orderId = orderId;
        this.unit = unit;
        this.price = price;
        this.purchasedAmount = purchasedAmount;
    }
}
