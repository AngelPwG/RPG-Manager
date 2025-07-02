package com.rpgmanager.models;

public class Session {
    private int id;
    private String createdAt;
    private String endedAt;
    private String name;
    private String resume;

    public Session(int id, String createdAt, String endedAt, String name, String resume) {
        this.id = id;
        this.createdAt = createdAt;
        this.endedAt = endedAt;
        this.name = name;
        this.resume = resume;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
