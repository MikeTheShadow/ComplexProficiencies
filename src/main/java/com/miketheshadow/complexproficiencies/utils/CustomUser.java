/*
 *
 *  * Copyright 2019-2020 Michael Pape and contributors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.miketheshadow.complexproficiencies.utils;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CustomUser {
    private final String name;
    private final String uid;
    private HashMap<String, Integer> professions = new HashMap<>();
    private HashMap<String, Integer> purses = new HashMap<>();

    private double lastHP = 1;

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
        this.lastHP = document.getDouble("lastHP");
    }

    public double getLastHP() { return lastHP; }

    public void setLastHP(double lastHP) { this.lastHP = lastHP; }

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

    public int getProfessionLevelByName(String name) {
        if(professions.containsKey(name)) return professions.get(name);
        else {
            professions.put(name,0);
            return 0;
        }
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
        document.append("lastHP",lastHP);
        return document;
    }

    public void addExperience(String profName, int amount, Player player) {

        labor -= amount;
        if(this.professions.get(profName) == null) {
            this.professions.put(profName,0);
            UserDBHandler.updatePlayer(this);
        }
        int experience = this.professions.get(profName);
        int before = getLevelFromTotal(this.professions.get(profName));
        this.professions.replace(profName, experience + amount);
        int after = getLevelFromTotal(this.professions.get(profName));
        int nextlevel = getRequiredExperience(after + 1) - getRequiredExperience(after);
        int exp = nextlevel - (getRequiredExperience(after + 1) - (experience + amount));

        String gainMessage = ChatColor.GRAY + "You gained " + ChatColor.GREEN + amount + ChatColor.GRAY + " experience in " + ChatColor.GOLD + profName.toLowerCase() + ChatColor.RESET;
        gainMessage += (ChatColor.GRAY + " [" + ChatColor.GREEN + exp + ChatColor.GRAY + "/" + ChatColor.GREEN + nextlevel + ChatColor.GRAY + "]");

        player.sendMessage(gainMessage);
        if (before != after) player.sendMessage(ChatColor.GRAY + "Your " + ChatColor.GOLD + profName.toLowerCase() + ChatColor.GRAY + " has reached level " + ChatColor.GREEN + after);
        player.sendMessage(ChatColor.YELLOW + "You currently have [" + ChatColor.GOLD + (getLabor()) + ChatColor.GRAY + "/" + ChatColor.GOLD + LaborThread.MAX_LABOR_STRING + ChatColor.GRAY + "]" + ChatColor.YELLOW + " labor!");
        UserDBHandler.updatePlayer(this);
    }

    public int getLevelFromTotal(int totalExperience) {
        return (int) ((-50 + (Math.sqrt(2500 + (200 * (totalExperience + 100))))) / (100));
    }
    public int getRequiredExperience(int level) {
        return (int) ((50 * level) + (50 * (Math.pow(level, 2))) - 100);
    }

    public int getLevelFromProf(String prof) {
        prof = prof.toLowerCase();
        if(!professions.containsKey(prof)) {
            professions.put(prof,0);
            UserDBHandler.updatePlayer(this);
            return 0;
        }
        return getLevelFromTotal(professions.get(prof));
    }
}
