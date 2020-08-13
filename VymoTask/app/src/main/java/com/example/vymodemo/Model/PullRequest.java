package com.example.vymodemo.Model;

public class PullRequest {
    private String number, title, created_at, state;

    public PullRequest(String number, String title, String created_at, String state) {
        this.number = number;
        this.title = title;
        this.created_at = created_at;
        this.state = state;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
