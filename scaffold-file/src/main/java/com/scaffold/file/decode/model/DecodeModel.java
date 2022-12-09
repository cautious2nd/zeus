package com.scaffold.file.decode.model;

public class DecodeModel<T> {
    private String body;
    private T t;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
