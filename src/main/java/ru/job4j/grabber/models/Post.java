package ru.job4j.grabber.models;

import java.util.Date;

public class Post {
    private int id;
    private String url;
    private String title;
    private String text;
    private Date date;

    public Post() {
    }

    public Post(String url, String title, String text, Date date) {
        this.url = url;
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public Post(int id, String url, String title, String text, Date date) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
