package com.example.v_vote.helpingclasses;

public class ElectionsListClass {
    public ElectionsListClass( ) { }

    public ElectionsListClass(String electionTitle, String electionDate, String candidate1, String candidate2, String resultDate) {
        this.electionTitle = electionTitle;
        this.electionDate = electionDate;
        this.candidate1 = candidate1;
        this.candidate2 = candidate2;
        this.resultDate = resultDate;
    }

    public String getElectionTitle() {
        return electionTitle;
    }

    public void setElectionTitle(String electionTitle) {
        this.electionTitle = electionTitle;
    }

    public String getElectionDate() {
        return electionDate;
    }

    public void setElectionDate(String electionDate) {
        this.electionDate = electionDate;
    }

    public String getCandidate1() {
        return candidate1;
    }

    public void setCandidate1(String candidate1) {
        this.candidate1 = candidate1;
    }

    public String getCandidate2() {
        return candidate2;
    }

    public void setCandidate2(String candidate2) {
        this.candidate2 = candidate2;
    }

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }

    String electionTitle,electionDate,candidate1,
            candidate2,resultDate;

}
