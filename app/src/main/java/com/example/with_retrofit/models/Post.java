package com.example.with_retrofit.models;

import java.io.Serializable;

public class Post {
  private int userId;
    private Integer id;
     private String title;
    private String body;

    public Post(int userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
    public Post(int id ,int userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.id =  id;
    }
    public Post() {

    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return this.title +" "+ this.id  +" "+  this.userId  +" "+  this.body;
    }
}
