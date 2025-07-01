package com.rpgmanager.models;

public class Character {
    private int id;
    private int campaign_id;
    private int stats_id;
    private boolean isNPC;
    private int ac;
    private int hp;
    private String name;
    private String race;
    private String classes;
    private String bonds;
    private String fears;
    private String loves;
    private String hates;

    public Character(int id, int campaign_id, boolean isNPC, int ac, int hp, int stats_id, String name, String race, String classes, String bonds, String fears, String loves, String hates) {
        this.id = id;
        this.campaign_id = campaign_id;
        this.isNPC = isNPC;
        this.hp = hp;
        this.ac = ac;
        this.stats_id = stats_id;
        this.name = name;
        this.race = race;
        this.classes = classes;
        this.bonds = bonds;
        this.fears = fears;
        this.loves = loves;
        this.hates = hates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(int campaign_id) {
        this.campaign_id = campaign_id;
    }

    public int getStats_id() {
        return stats_id;
    }

    public void setStats_id(int stats_id) {
        this.stats_id = stats_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getBonds() {
        return bonds;
    }

    public void setBonds(String bonds) {
        this.bonds = bonds;
    }

    public String getFears() {
        return fears;
    }

    public void setFears(String fears) {
        this.fears = fears;
    }

    public String getLoves() {
        return loves;
    }

    public void setLoves(String loves) {
        this.loves = loves;
    }

    public String getHates() {
        return hates;
    }

    public void setHates(String hates) {
        this.hates = hates;
    }

    public boolean isNPC() {
        return isNPC;
    }

    public void setNPC(boolean NPC) {
        isNPC = NPC;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
