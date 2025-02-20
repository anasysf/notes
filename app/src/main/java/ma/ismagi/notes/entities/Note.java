package ma.ismagi.notes.entities;

import androidx.annotation.NonNull;

import java.util.Date;

public class Note {
    private String id;
    private String userId;
    private String title;
    private String content;
    private Date timestamp; // Optional, to store when the note was created

    // Required no-argument constructor for Firebase
    public Note() {}

    public Note(String userId, String title, String content, Date timestamp) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(@NonNull String id) { this.id = id; }
    public String getUserId() { return userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}

