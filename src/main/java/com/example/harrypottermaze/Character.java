package com.example.harrypottermaze;

public class Character {

    private String name;
    private String imageUrl;

    public Character(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}