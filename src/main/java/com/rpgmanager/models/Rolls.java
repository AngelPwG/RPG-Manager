package com.rpgmanager.models;

import java.time.LocalDateTime;

public class Rolls {
    private int id;
    private String character;
    private String type;
    private String session;
    private String campaign;
    private String dateTime;
    private String result;

    public Rolls(int id, String character, String type, String session, String campaign, String dateTime, String result) {
        this.id = id;
        this.character = character;
        this.type = type;
        this.session = session;
        this.campaign = campaign;
        this.dateTime = dateTime;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
