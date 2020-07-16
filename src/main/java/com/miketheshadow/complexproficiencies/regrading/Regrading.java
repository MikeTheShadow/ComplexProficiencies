package com.miketheshadow.complexproficiencies.regrading;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static boolean checkData(ItemStack stack) {
        if(!stack.hasItemMeta())return false;
        if(!stack.getItemMeta().hasLore())return false;
        return true;
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
    public static void regradeItem(Player player, ItemStack stack, ItemStack scroll) {
        String grade = getCurrentGrade(stack);
        float chance = Grade.regradeChance.get(grade);
        float r = (float) (Math.random() * (100));
        String currentGrade = ChatColor.stripColor(grade);
        if(r <= chance) {
            Bukkit.broadcastMessage("You regraded the item woot!");
            String nextGrade = ChatColor.stripColor(getNextGrade(stack));
            String id = NBTItem.convertItemtoNBT(stack).getCompound("tag").getString("MMOITEMS_ITEM_ID");
            String type = NBTItem.convertItemtoNBT(stack).getCompound("tag").getString("MMOITEMS_ITEM_TYPE");
            id = id.replace(currentGrade.toUpperCase(),nextGrade.toUpperCase());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mmoitems " + type + " " + id + " " + player.getName() + " " + 1); //mmoitems (weapon type) (ID) (playername) (amount)
            player.getInventory().removeItem(stack);
            player.sendMessage(ChatColor.YELLOW + "You have successfully regraded your item.");
        } else {
            //player failed regrade oof
            player.sendMessage(ChatColor.YELLOW + "You have failed to regrade your item.");
            if(Grade.gradeList.indexOf(grade) > Grade.gradeList.indexOf(Grade.ARCANE)) {
                //give player Arcane version of item
                String id = NBTItem.convertItemtoNBT(stack).getCompound("tag").getString("MMOITEMS_ITEM_ID");
                String type = NBTItem.convertItemtoNBT(stack).getCompound("tag").getString("MMOITEMS_ITEM_TYPE");
                id = id.replace(currentGrade.toUpperCase(),ChatColor.stripColor(Grade.ARCANE.toUpperCase()));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mmoitems " + type + " " + id + " " + player.getName() + " " + 1);
                player.getInventory().removeItem(stack);
            }
        }
        //remove regrade scroll from inventory
        player.getInventory().removeItem(scroll);
    }
}
