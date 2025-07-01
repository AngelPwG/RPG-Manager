package com.rpgmanager.models;

public class Items {
    private int id;
    private String name;
    private String category;
    private String description;
    private String damage_dice;

    public Items(int id, String name, String category, String description, String damage_dice) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.damage_dice = damage_dice;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDamage_dice() {
        return damage_dice;
    }

    public void setDamage_dice(String damage_dice) {
        this.damage_dice = damage_dice;
    }
}
