package com.example.newsapp;

public class ModelClass {

    String title;
    String urlToImage;
    String url;
    String description;
    String source;

    public ModelClass(String title, String urlToImage, String url, String description, String source) {
        this.title = title;
        this.urlToImage = urlToImage;
        this.url = url;
        this.description = description;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
