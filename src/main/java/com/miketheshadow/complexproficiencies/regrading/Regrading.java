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

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.api.UserAPI;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Regrading {

    public static final String IS_REGRADABLE = ChatColor.GOLD + "Regradable";
    public static final String INVENTORY_TITLE = ChatColor.YELLOW + "Regrade Item";

    public static final String WEAPON_REGRADE_SCROLL = "Weapon Regrade Scroll";
    public static final String ARMOR_REGRADE_SCROLL = "Armor Regrade Scroll";

    public static boolean isRegradable(ItemStack stack) {
        if(!checkData(stack)) return false;
        for (String value: stack.getItemMeta().getLore()) {
            if(value.contains(IS_REGRADABLE))return true;
        }
        return false;
    }

    public static boolean isWeapon(ItemStack stack) {
        if(!checkData(stack))return false;
        for(String lore : stack.getItemMeta().getLore()) {
            if(lore.contains("Weapon")) return true;
        }
        return true;
    }

    public static boolean isArmor(ItemStack stack) {
        if(!checkData(stack))return false;
        for(String lore : stack.getItemMeta().getLore()) {
            if(lore.contains("Armor")) return true;
        }
        return true;
    }

    public static boolean isRegradeButton(ItemStack stack) {
        if(!stack.hasItemMeta())return false;
        if(!stack.getItemMeta().hasDisplayName())return false;
        return stack.getItemMeta().getDisplayName().equals(Grade.getRegradeButton().getItemMeta().getDisplayName());
    }

    public static boolean checkData(ItemStack stack) {
        if(!stack.hasItemMeta())return false;
        return stack.getItemMeta().hasLore();
    }

    public static String getNextGrade(ItemStack stack) {
        for(String lore : stack.getItemMeta().getLore()) {
            for(String grade : Grade.gradeList) {
                if(lore.contains(grade)) return Grade.gradeList.get(Grade.gradeList.indexOf(grade) + 1);
            }
        }
        return "null";
    }

    public static String getCurrentGrade(ItemStack stack) {
        for(String lore : stack.getItemMeta().getLore()) {
            for(String grade : Grade.gradeList) {
                if(lore.contains(grade)) return Grade.gradeList.get(Grade.gradeList.indexOf(grade));
            }
        }
        return "null";
    }

    public static void regradeItem(Player player, ItemStack stack, ItemStack scroll,int moneyCost) {
        if(ComplexProficiencies.econ.getBalance(player) < moneyCost) {
            player.sendMessage(ChatColor.RED + "You don't have enough money!");
            return;
        } else if(!UserAPI.userHasLabor(player,10)) {
            player.sendMessage(ChatColor.RED + "You don't have enough Labor!");
            return;
        } else if(!player.getInventory().containsAtLeast(scroll,1)) {
            player.sendMessage(ChatColor.RED + "you don't have enough " + scroll.getItemMeta().getDisplayName());
            return;
        }
        String grade = getCurrentGrade(stack);
        if(isWeapon(stack) && !scroll.getItemMeta().getDisplayName().contains(WEAPON_REGRADE_SCROLL)) {
            player.sendMessage(ChatColor.RED + "You need to use a " + WEAPON_REGRADE_SCROLL);
            return;
        } else if(isArmor(stack) && !scroll.getItemMeta().getDisplayName().contains(ARMOR_REGRADE_SCROLL)) {
            player.sendMessage(ChatColor.RED + "You need to use a " + ARMOR_REGRADE_SCROLL);
            return;
        }
        float chance = Grade.regradeChance.get(grade);
        float r = (float) (Math.random() * (100));
        String currentGrade = ChatColor.stripColor(grade);
        if(r <= chance) {
            String nextGrade = ChatColor.stripColor(getNextGrade(stack));
            String id = NBTItem.convertItemtoNBT(stack).getCompound("tag").getString("MMOITEMS_ITEM_ID");
            String type = NBTItem.convertItemtoNBT(stack).getCompound("tag").getString("MMOITEMS_ITEM_TYPE");
            id = id.replace(currentGrade.toUpperCase(),nextGrade.toUpperCase());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mmoitems " + type + " " + id + " " + player.getName() + " " + 1); //mmoitems (weapon type) (ID) (playername) (amount)
            player.getInventory().removeItem(stack);
            player.sendMessage(ChatColor.YELLOW + "You have successfully regraded your item.");
        } else {
            //player failed regrade oof
            if(Grade.gradeList.indexOf(grade) > Grade.gradeList.indexOf(Grade.HEROIC)) {
                //give player Arcane version of item
                String id = NBTItem.convertItemtoNBT(stack).getCompound("tag").getString("MMOITEMS_ITEM_ID");
                String type = NBTItem.convertItemtoNBT(stack).getCompound("tag").getString("MMOITEMS_ITEM_TYPE");
                id = id.replace(currentGrade.toUpperCase(),ChatColor.stripColor(Grade.ARCANE.toUpperCase()));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mmoitems " + type + " " + id + " " + player.getName() + " " + 1);
                player.getInventory().removeItem(stack);
                player.sendMessage(ChatColor.YELLOW + "You have failed to regrade your item and it has been downgraded to arcane.");
            } else {
                player.sendMessage(ChatColor.YELLOW + "You have failed to regrade your item.");
            }
        }
        if(Grade.gradeList.indexOf(grade) > 6)
        //remove regrade scroll from inventory
        ComplexProficiencies.econ.withdrawPlayer(player,moneyCost);
        UserAPI.addExperienceToProf(player,"alchemy",10);
        player.getInventory().removeItem(scroll);
        player.getOpenInventory().getTopInventory().setItem(20,null);
    }
}
