package com.technexia.user.myapplication;

/**
 * Created by User on 2/1/2018.
 */

public class User  extends UserId{
    String name;
    String email;
    String imageUri;

    public User() {

    }

    public User(String name, String email, String imageUri) {
        this.name = name;
        this.email = email;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
