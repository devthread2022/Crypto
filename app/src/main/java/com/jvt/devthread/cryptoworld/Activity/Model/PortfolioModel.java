package com.jvt.devthread.cryptoworld.Activity.Model;

public class PortfolioModel {
    double currentValue, investedValue, gainLossValue;

    public PortfolioModel() {
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getInvestedValue() {
        return investedValue;
    }

    public void setInvestedValue(double investedValue) {
        this.investedValue = investedValue;
    }

    public double getGainLossValue() {
        return gainLossValue;
    }

    public void setGainLossValue(double gainLossValue) {
        this.gainLossValue = gainLossValue;
    }

    public PortfolioModel(double currentValue, double investedValue, double gainLossValue) {
        this.currentValue = currentValue;
        this.investedValue = investedValue;
        this.gainLossValue = gainLossValue;
    }
}
