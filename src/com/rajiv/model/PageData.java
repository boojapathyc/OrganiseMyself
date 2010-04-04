package com.rajiv.model;

public class PageData {
    String name;
    String content;

    public PageData(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public PageData() {

    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }
}
