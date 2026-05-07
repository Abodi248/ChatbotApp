package com.example.chatbotapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "messages")
public class Message {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String username;
    public String content;
    public boolean isUser;
    public long timestamp;

    public Message(String username, String content, boolean isUser, long timestamp) {
        this.username = username;
        this.content = content;
        this.isUser = isUser;
        this.timestamp = timestamp;
    }
}
