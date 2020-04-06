package com.example.myapplication.loaddataapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainElement {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("rows")
    @Expose
    private List<ItemElement> items = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ItemElement> getItems() {
        return items;
    }

    public void setItems(List<ItemElement> items) {
        this.items = items;
    }
}