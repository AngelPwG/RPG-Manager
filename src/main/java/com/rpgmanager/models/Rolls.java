package com.rpgmanager.models;

import java.time.LocalDateTime;

public class Rolls {
    private int id;
    private int characterId;
    private String type;
    private int campaignId;
    private LocalDateTime dateTime;
    private String result;

    public Rolls(int id, int characterId, String type, int campaignId, LocalDateTime dateTime, String result) {
        this.id = id;
        this.characterId = characterId;
        this.type = type;
        this.campaignId = campaignId;
        this.dateTime = dateTime;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
