package com.dilip.networksecurityconfig.model;

public class Post {
    public String name;
    public String message;
    public String profileImage;

    public Post() {
        // Empty constructor is required for Gson.
    }

    public Post(String name, String message, String profileImage) {
        this.name = name;
        this.message = message;
        this.profileImage = profileImage;
    }
}
