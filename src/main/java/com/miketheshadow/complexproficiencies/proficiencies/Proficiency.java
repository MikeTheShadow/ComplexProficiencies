package com.miketheshadow.complexproficiencies.proficiencies;

import java.io.Serializable;

public class Proficiency implements Serializable {
    double experience = 0;
    double totalExperience = 0;
    int level = 0;
    String name;

    public Proficiency(double experience, double totalExperience, int level, String name) {
        this.experience = experience;
        this.totalExperience = totalExperience;
        this.level = level;
        this.name = name;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public double getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(double totalExperience) {
        this.totalExperience = totalExperience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
