package com.example.studentbehaviouranalysis;

import android.graphics.Bitmap;

public class ListItem2 {

    private String faceID, emotion;
    private Bitmap image;

    public ListItem2(String faceID, String emotion, Bitmap image) {
        this.faceID = faceID;
        this.emotion = emotion;
        this.image = image;
    }

    public String getFaceID() {
        return faceID;
    }

    public String getEmotion() {
        return emotion;
    }

    public Bitmap getImage() {
        return image;
    }
}
