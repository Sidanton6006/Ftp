package com.example.ftpserver.API;

public class Image {
    private  String base64image;

    public Image(String base64image){
        this.base64image = base64image;
    }

    public String getString() {
        return base64image;
    }

    public void setString(String base64image) {
        this.base64image = base64image;
    }
}
