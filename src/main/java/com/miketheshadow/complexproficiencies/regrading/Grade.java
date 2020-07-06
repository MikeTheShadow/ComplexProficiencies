package com.miketheshadow.complexproficiencies.regrading;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Grade {

    public static List<String> gradeList = new ArrayList<>();

    public static final String CRUDE = register(ChatColor.GRAY ,"Crude");
    public static final String BASIC = register(ChatColor.WHITE , "Basic");
    public static final String GRAND = register(ChatColor.GREEN , "Grand");
    public static final String RARE = register(ChatColor.BLUE , "Rare");
    public static final String ARCANE = register(ChatColor.LIGHT_PURPLE , "Arcane");
    public static final String HEROIC = register(ChatColor.YELLOW , "Heroic");
    public static final String UNIQUE = register(ChatColor.DARK_AQUA , "Unique");
    public static final String CELESTIAL = register(ChatColor.RED , "Celestial");
    public static final String DIVINE = register(ChatColor.DARK_PURPLE , "Divine");
    public static final String EPIC = register(ChatColor.AQUA, "Epic");
    public static final String LEGENDARY = register(ChatColor.GOLD , "Legendary");
    public static final String MYTHIC = register(ChatColor.DARK_RED, "Mythic");


    public static String register(ChatColor color,String grade) {
        grade = color + "" + ChatColor.BOLD + ChatColor.ITALIC + grade;
        gradeList.add(grade);
        return grade;
    }
}
