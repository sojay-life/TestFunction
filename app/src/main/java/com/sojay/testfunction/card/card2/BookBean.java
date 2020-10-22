package com.sojay.testfunction.card.card2;

import java.io.Serializable;

public class BookBean implements Serializable {

    private String name;
    private String cover;

    public BookBean() {

    }

    public BookBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

}
