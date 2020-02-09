package com.miketheshadow.complexproficiencies.Database;

public class CustomUser
{
    String name;
    String UID;
    int level;
    int currentXP;
    int totalXP;

    public CustomUser(String name, String UID, int level, int currentXP, int totalXP)
    {
        this.name = name;
        this.UID = UID;
        this.level = level;
        this.currentXP = currentXP;
        this.totalXP = totalXP;
    }

    //getters
    public String getName() {return name;}
    public String  getUID() {return UID;}
    public int getLevel(){return level;}
    public int getCurrentXP() {return currentXP;}
    public int getTotalXP() {return totalXP;}

    //setters
    public void setXP(int change) { this.currentXP = change; }
    public void setTotalXP(int change) { this.totalXP = change; }
    public void setLevel(int change) { this.level = change; }

    //additions
    public void addXP(int change){ this.currentXP += change; }
    public void addTotalXP(int change) { this.totalXP += change; }
    public void addLevel(int change) { this.level += change; }

}
