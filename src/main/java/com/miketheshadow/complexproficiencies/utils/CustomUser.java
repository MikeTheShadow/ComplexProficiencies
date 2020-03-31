package com.miketheshadow.complexproficiencies.utils;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CustomUser {
    private String name;
    private String uid;
    private HashMap<String, Integer> professions = new HashMap<>();
    private HashMap<String, Integer> purses = new HashMap<>();

    //level then experience
    private int[] levelXP;

    private int balance = 0;
    private int labor = 0;

    public CustomUser(String name, String uid) {
        this.name = name;
        this.uid = uid;
        setProfessions();
        levelXP = new int[] {1,0};
    }

    //from document
    public CustomUser(Document document) {
        this.name = document.getString("name");
        this.uid = document.getString("uid");
        this.balance = document.getInteger("balance");
        this.labor = document.getInteger("labor");
        BasicDBObject prof = new BasicDBObject((Document)document.get("professions"));
        this.professions = (HashMap<String, Integer>) prof.toMap();
        BasicDBObject purse = new BasicDBObject((Document)document.get("purses"));
        this.purses = (HashMap<String, Integer>) purse.toMap();
        this.levelXP = new int[] {document.getInteger("level"),document.getInteger("xp")};
    }

    public int[] getLevelXP() { return levelXP; }
    public void setLevelXP(int[] levelXP) { this.levelXP = levelXP; }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public int getBalance() { return balance; }
    public void setBalance(int balance) { this.balance = balance; }

    public int getLabor() { return labor; }
    public void setLabor(int labor) { this.labor = labor; }

    public HashMap<String, Integer> getPurses() { return purses; }
    public void setPurses(HashMap<String, Integer> purses) { this.purses = purses; }

    public HashMap<String, Integer> getProfessions() {
        return professions;
    }

    public void setProfessions(HashMap<String, Integer> professions) {
        this.professions = professions;
    }

    private void setProfessions() {
        for (String prof : ComplexProficiencies.profList) {
            professions.put(prof, 0);
        }
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("name", name);
        document.append("uid", uid);
        document.append("balance",balance);
        document.append("labor",labor);
        document.append("professions",professions);
        document.append("purses",purses);
        document.append("level",levelXP[0]);
        document.append("xp",levelXP[1]);
        return document;
    }

    public void addExperience(String profName, int amount, Player player) {
        int experience = this.professions.get(profName);
        int before = getLevelFromTotal(this.professions.get(profName));
        this.professions.replace(profName, experience + amount);
        int after = getLevelFromTotal(this.professions.get(profName));
        player.sendMessage(ChatColor.GRAY + "You gained " + ChatColor.GREEN + amount + ChatColor.GRAY + "experience in " + ChatColor.GOLD + profName.toLowerCase());

        int nextlevel = getRequiredExperience(after + 1) - getRequiredExperience(after);
        int exp = nextlevel - (getRequiredExperience(after + 1) - (experience + amount));
        player.sendMessage(ChatColor.GREEN + String.valueOf(exp) + ChatColor.GRAY + "/" + ChatColor.GREEN + nextlevel);
        if (before != after)
            player.sendMessage(ChatColor.GRAY + "Your " + ChatColor.GOLD + profName.toLowerCase() + ChatColor.GRAY + " has reached level " + ChatColor.GREEN + after);
    }

    public int getLevelFromTotal(int totalExperience) {
        return (int) ((-1450 + (Math.sqrt(2102500 + (400 * totalExperience)))) / (200));
    }
    public int getRequiredExperience(int level) {
        return (int) ((1450 * level) + (100 * (Math.pow(level, 2))));
    }

    public int getLevelFromProf(String prof)
    {
        return getLevelFromTotal(professions.get(prof));
    }

}
