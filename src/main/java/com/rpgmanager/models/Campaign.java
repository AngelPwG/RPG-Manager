package com.rpgmanager.models;


import java.time.LocalDateTime;

public class Campaign {
    private int id;
    private String name;
    private String system;
    private String description;
    private LocalDateTime created_at;
    private String state;

    public Campaign(int id, String name, String system, String description, LocalDateTime created_at, String state) {
        this.id = id;
        this.name = name;
        this.system = system;
        this.description = description;
        this.created_at = created_at;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }
}
