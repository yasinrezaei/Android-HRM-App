package com.example.aseman.Objects;

public class ManagerDailyReport {
    int id;
    int sender_id;
    String text;
    String answer;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ManagerDailyReport(int id,int sender_id, String text, String answer,String date) {
        this.id=id;
        this.sender_id = sender_id;
        this.text = text;
        this.answer = answer;
        this.date = date;
    }

    public ManagerDailyReport() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

