package com.finz.rest.slider.entity;

import java.io.Serializable;

public class Slider implements Serializable {

    private long id;
    private String title;
    private String image;
    private String message;
    private boolean status;

    public Slider() {
    }

    public Slider(long id, String title, String image, String message, boolean status) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.message = message;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Slider{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
