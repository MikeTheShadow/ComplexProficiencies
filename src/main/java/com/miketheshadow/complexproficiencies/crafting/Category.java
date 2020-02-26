package com.miketheshadow.complexproficiencies.crafting;

import org.bson.Document;

public class Category {

    String title;
    String icon;
    String path;

    public Category(String title, String icon, String path) {
        this.title = title;
        this.icon = icon;
        this.path = path;
    }

    public Category(Document document) {
        this.title = document.getString("name");
        this.icon = document.getString("icon");
        this.path = document.getString("path");
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("name", title);
        document.append("icon", icon);
        document.append("path", path);
        return document;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
