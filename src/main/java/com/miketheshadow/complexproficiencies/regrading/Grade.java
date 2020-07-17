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

package com.miketheshadow.complexproficiencies.regrading;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Grade {

    public static List<String> gradeList = new ArrayList<>();
    public static HashMap<String,Float> regradeChance = new HashMap<>();

    public static final String CRUDE = register(ChatColor.GRAY ,"Crude",100);
    public static final String BASIC = register(ChatColor.WHITE , "Basic",60);
    public static final String GRAND = register(ChatColor.GREEN , "Grand",40);
    public static final String RARE = register(ChatColor.BLUE , "Rare",30);
    public static final String ARCANE = register(ChatColor.LIGHT_PURPLE , "Arcane",30);
    public static final String HEROIC = register(ChatColor.YELLOW , "Heroic",25);
    public static final String UNIQUE = register(ChatColor.DARK_AQUA , "Unique",20);
    public static final String CELESTIAL = register(ChatColor.RED , "Celestial",20);
    public static final String DIVINE = register(ChatColor.DARK_PURPLE , "Divine",10);
    public static final String EPIC = register(ChatColor.AQUA, "EPIC",7.5f);
    public static final String LEGENDARY = register(ChatColor.GOLD , "LEGENDARY",5);
    public static final String MYTHIC = register(ChatColor.DARK_RED, "MYTHIC",2.5f);


    public static String register(ChatColor color,String grade,float regradeChance) {
        grade = color + "" + ChatColor.BOLD + ChatColor.ITALIC + grade;
        gradeList.add(grade);
        Grade.regradeChance.put(grade,regradeChance);
        return grade;
    }
}
