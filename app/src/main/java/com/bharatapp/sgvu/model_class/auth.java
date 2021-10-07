package com.bharatapp.sgvu.model_class;

public class auth {
    int id;
    String token;
    public auth() {
    }
    public auth(int id, String token) {
        this.id = id;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
