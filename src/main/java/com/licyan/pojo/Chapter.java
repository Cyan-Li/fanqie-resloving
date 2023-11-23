package com.licyan.pojo;

public class Chapter {

    private String title;

    private String href;

    public Chapter(String title, String href) {
        this.title = title;
        this.href = href;
    }

    public Chapter() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
