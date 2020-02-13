package com.miketheshadow.complexproficiencies.utils;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CustomPlayer
{
    private String name;
    private String uid;
    private HashMap<String,Integer> professions = new HashMap<>();

    public String getName() { return name; }
    public String getUid() { return uid; }
    public HashMap<String, Integer> getProfessions() { return professions; }
    public void setProfessions(HashMap<String, Integer> professions) { this.professions = professions; }


    public CustomPlayer(String name,String uid)
    {
        this.name = name;
        this.uid = uid;
        setProfessions();
    }
    public CustomPlayer(String name, String uid, HashMap<String,Integer> professions)
    {
        this.name = name;
        this.uid = uid;
        this.professions = professions;
    }
    public CustomPlayer(Document document)
    {
        this.name = document.getString("name");
        this.uid = document.getString("uid");
        for(String prof : ComplexProficiencies.profList)
        {
            professions.put(prof,document.getInteger(prof));
        }
    }

    private void setProfessions()
    {
        for (String prof:ComplexProficiencies.profList)
        {
            professions.put(prof,0);
        }
    }

    public Document toDocument()
    {
        Document document = new Document();
        document.append("name",name);
        document.append("uid",uid);
        for (Map.Entry<String,Integer> map : professions.entrySet())
        {
            document.append(map.getKey(),map.getValue());
        }
        return document;
    }

    public void addExperience(String profName, int amount, Player player)
    {
        int experience = this.professions.get(profName);
        int before = getLevelFromTotal(this.professions.get(profName));
        this.professions.replace(profName,experience + amount);
        int after = getLevelFromTotal(this.professions.get(profName));
        player.sendMessage(ChatColor.GRAY + "You gained "+ ChatColor.GREEN + amount + ChatColor.GRAY +"experience in " + ChatColor.GOLD + profName.toLowerCase());

        int nextlevel = getRequiredExperience(after + 1) - getRequiredExperience(after);
        int exp = nextlevel - (getRequiredExperience(after + 1) - (experience + amount));
        player.sendMessage(ChatColor.GREEN + String.valueOf(exp) + ChatColor.GRAY + "/" + ChatColor.GREEN + nextlevel);
        if(before != after)player.sendMessage(ChatColor.GRAY + "Your " + ChatColor.GOLD + profName.toLowerCase() + ChatColor.GRAY + " has reached level " + ChatColor.GREEN + after);
    }
    public int getLevelFromTotal(int totalExperience)
    {
        return (int)((-1450+(Math.sqrt(2102500+(400*totalExperience))))/(200));
    }
    public int getRequiredExperience(int level)
    {
        return (int)((1450*level)+(100*(Math.pow(level,2))));
    }
}
