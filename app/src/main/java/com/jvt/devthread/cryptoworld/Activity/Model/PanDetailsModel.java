package com.jvt.devthread.cryptoworld.Activity.Model;

public class PanDetailsModel {
    String panNumber, nameOnPan, dob, email;

    public PanDetailsModel() {
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getNameOnPan() {
        return nameOnPan;
    }

    public void setNameOnPan(String nameOnPan) {
        this.nameOnPan = nameOnPan;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PanDetailsModel(String panNumber, String nameOnPan, String dob, String email) {
        this.panNumber = panNumber;
        this.nameOnPan = nameOnPan;
        this.dob = dob;
        this.email = email;
    }
}
