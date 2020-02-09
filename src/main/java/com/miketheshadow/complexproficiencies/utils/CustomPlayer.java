package com.miketheshadow.complexproficiencies.utils;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import org.bson.Document;

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

    public void addExperience(String profName, int amount)
    {
        int experience = this.professions.get(profName);
        this.professions.replace(profName,experience + amount);
    }

}
