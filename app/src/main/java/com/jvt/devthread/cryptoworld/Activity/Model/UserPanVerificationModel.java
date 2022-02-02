package com.jvt.devthread.cryptoworld.Activity.Model;

public class UserPanVerificationModel {
    String panNumber, nameOnPan, dob, email, userUid, requestNumber;

    public UserPanVerificationModel() {
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

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public UserPanVerificationModel(String panNumber, String nameOnPan, String dob, String email, String userUid, String requestNumber) {
        this.panNumber = panNumber;
        this.nameOnPan = nameOnPan;
        this.dob = dob;
        this.email = email;
        this.userUid = userUid;
        this.requestNumber = requestNumber;
    }
}
