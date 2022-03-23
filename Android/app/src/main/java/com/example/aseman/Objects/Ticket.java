package com.example.aseman.Objects;

public class Ticket {
    int id;
    String title;
    String text;
    String answer;
    String tag;
    boolean checkByReciever;
    int sender_id;
    int reciever_id;

    public Ticket(int id, String title, String text, String answer, String tag, boolean checkByReciever, int sender_id, int reciever_id) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.answer = answer;
        this.tag = tag;
        this.checkByReciever = checkByReciever;
        this.sender_id = sender_id;
        this.reciever_id = reciever_id;
    }


    public Ticket() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setCheckByReciever(boolean checkByReciever) {
        this.checkByReciever = checkByReciever;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public void setReciever_id(int reciever_id) {
        this.reciever_id = reciever_id;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getAnswer() {
        return answer;
    }

    public String getTag() {
        return tag;
    }

    public boolean isCheckByReciever() {
        return checkByReciever;
    }

    public int getSender_id() {
        return sender_id;
    }

    public int getReciever_id() {
        return reciever_id;
    }
}
