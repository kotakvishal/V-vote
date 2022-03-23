package com.example.v_vote.helpingclasses;

public class ActiveUsersListClass {
    public ActiveUsersListClass(String commissionsName, String commissionersName,
                                String phoneNumber, String email, String aadharNumber) {
        this.commissionsName = commissionsName;
        this.commissionersName = commissionersName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.aadharNumber = aadharNumber;
    }
    public ActiveUsersListClass(){
    }

    public String phoneNumber;
    public String email;
    public String commissionsName;
    public String commissionersName;

    public String getCommissionsName() {
        return commissionsName;
    }

    public void setCommissionsName(String commissionsName) {
        this.commissionsName = commissionsName;
    }

    public String getCommissionersName() {
        return commissionersName;
    }

    public void setCommissionersName(String commissionersName) {
        this.commissionersName = commissionersName;
    }

    public String aadharNumber;


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

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }
}
