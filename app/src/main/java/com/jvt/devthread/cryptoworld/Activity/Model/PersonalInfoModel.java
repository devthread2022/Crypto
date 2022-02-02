package com.jvt.devthread.cryptoworld.Activity.Model;

public class PersonalInfoModel {
    String name, uid, email, mobile, address, kycStatus, profilePic,gender;

    public PersonalInfoModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(String kycStatus) {
        this.kycStatus = kycStatus;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public PersonalInfoModel(String name, String uid, String email, String mobile, String address, String kycStatus, String profilePic, String gender) {
        this.name = name;
        this.uid = uid;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.kycStatus = kycStatus;
        this.profilePic = profilePic;
        this.gender = gender;
    }
}
