package com.example.aseman.Objects;

public class Report {
    int id;
    String keyWord;
    String UrgentNeed;
    int userId;
    int analyzerId;


    public Report(int id, String keyWord, String urgentNeed, int userId, int analyzerId) {
        this.id = id;
        this.keyWord = keyWord;
        UrgentNeed = urgentNeed;
        this.userId = userId;
        this.analyzerId = analyzerId;
    }

    public Report() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getUrgentNeed() {
        return UrgentNeed;
    }

    public void setUrgentNeed(String urgentNeed) {
        UrgentNeed = urgentNeed;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAnalyzerId() {
        return analyzerId;
    }

    public void setAnalyzerId(int analyzerId) {
        this.analyzerId = analyzerId;
    }
}
