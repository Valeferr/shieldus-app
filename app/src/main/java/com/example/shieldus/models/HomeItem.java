package com.example.shieldus.models;

public class HomeItem {
    private String title;
    private int iconResId;

    public HomeItem(String title, int iconResId) {
        this.title = title;
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public int getIconResId() {
        return iconResId;
    }
}