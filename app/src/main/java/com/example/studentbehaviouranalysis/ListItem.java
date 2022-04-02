package com.example.studentbehaviouranalysis;

import android.graphics.Bitmap;

public class ListItem {

    private String emotion;
    private Bitmap image;

    public ListItem(String emotion, Bitmap image) {
        this.emotion = emotion;
        this.image = image;
    }

    public String getEmotion() {
        return emotion;
    }

    public Bitmap getImage() {
        return image;
    }
}
