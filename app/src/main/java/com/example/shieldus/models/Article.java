package com.example.shieldus.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {
    private String id;
    private String title;
    private String summary;
    private String content;
    private String imageUrl;
    private String category;
    private String date;

    public Article(String id, String title, String summary, String content, String imageUrl, String category, String date) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.imageUrl = imageUrl;
        this.category = category;
        this.date = date;
    }

    protected Article(Parcel in) {
        id = in.readString();
        title = in.readString();
        summary = in.readString();
        content = in.readString();
        imageUrl = in.readString();
        category = in.readString();
        date = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getSummary() { return summary; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
    public String getCategory() { return category; }
    public String getDate() { return date; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(summary);
        dest.writeString(content);
        dest.writeString(imageUrl);
        dest.writeString(category);
        dest.writeString(date);
    }
}