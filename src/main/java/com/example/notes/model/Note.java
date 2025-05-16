package main.java.com.example.notes.model;

public class Note {
    private String email;
    private String tag;
    private String content;

    public Note() {}

    public Note(String email, String tag, String content) {
        this.email = email;
        this.tag = tag;
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public String getTag() {
        return tag;
    }

    public String getContent() {
        return content;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setContent(String content) {
        this.content = content;
    }
}