package com.jvt.devthread.cryptoworld.Activity.Model;

public class BankDetailModel {
    String bankName, accountNumber, IfsCode;

    public BankDetailModel() {
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfsCode() {
        return IfsCode;
    }

    public void setIfsCode(String ifsCode) {
        IfsCode = ifsCode;
    }

    public BankDetailModel(String bankName, String accountNumber, String ifsCode) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        IfsCode = ifsCode;
    }
}
