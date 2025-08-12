package com.example.trekbuddy;

public class Messages {
    private String senderId;
    private String receiverId; // Added receiverId to identify the recipient
    private String content;
    private long timestamp;

    public Messages() {
        // Default constructor required for calls to DataSnapshot.getValue(Messages.class)
    }

    public Messages(String senderId, String receiverId, String content, long timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId; // Initialize receiverId
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId; // Getter for receiverId
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId; // Setter for receiverId
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
