package com.example.trekbuddy;

public class TrekkingLocation {

    private String id;
    private String title;
    private String googleLink;

    // Default constructor required for calls to DataSnapshot.getValue(TrekkingLocation.class)
    public TrekkingLocation() {}

    public TrekkingLocation(String id, String title, String googleLink) {
        this.id = id;
        this.title = title;
        this.googleLink = googleLink;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGoogleLink() {
        return googleLink;
    }
}

