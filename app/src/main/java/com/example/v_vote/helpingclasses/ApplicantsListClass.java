package com.example.v_vote.helpingclasses;

public class ApplicantsListClass {
    public String getCommissionersName() {
        return commissionersName;
    }

    public void setCommissionersName(String commissionersName) {
        this.commissionersName = commissionersName;
    }

    public String getCommissionsName() {
        return commissionsName;
    }

    public void setCommissionsName(String commissionsName) {
        this.commissionsName = commissionsName;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ApplicantsListClass(){ }
    public ApplicantsListClass(String commissionersName, String commissionsName, String aadharNumber
            ,String UserId, String phoneNumber, String email) {
        this.commissionersName = commissionersName;
        this.commissionsName = commissionsName;
        this.aadharNumber = aadharNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    String commissionersName;
    String commissionsName;
    String aadharNumber;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    String UserId;




    String phoneNumber;
    String email;
}
