package com.example.shieldus.models;

public class EducationModule {
    private String id;
    private String title;
    private String description;
    private String detailedContent;
    private int iconResId;
    private int progress;
    private boolean isCompleted;


    public EducationModule(String id, String title, String description, String detailedContent, int iconResId, int progress) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.detailedContent = detailedContent;
        this.iconResId = iconResId;
        this.progress = progress;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getIconResId() { return iconResId; }
    public String getDetailedContent() {
        return detailedContent;
    }
    public int getProgress() { return progress; }

    public boolean isCompleted() {
        return progress >= 100;
    }
}