package com.miketheshadow.complexproficiencies.regrading;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Regrading {

    public static final String IS_REGRADABLE = ChatColor.GOLD + "Regrade Available";
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
    public static ItemStack startRegrade(ItemStack stack) {
        NBTContainer container = NBTItem.convertItemtoNBT(stack);
        if(!container.getKeys().contains("tag")) {
            return null;
        }
        if(!container.getCompound("tag").hasKey("MMOITEMS_LORE")) return null;
        String loreString = container.getCompound("tag").getString("MMOITEMS_LORE");
        float damage = 0f;
        int totalDurability = 0;
        int currentDurability = 0;
        //ew regex
        for(String s : loreString.split("\",\"")) {
            if(s.contains("Damage")) {
                s = ChatColor.stripColor(s);
                s = s.replace("Damage:","").replaceAll("[ ]","");
                damage = Float.parseFloat(s);
            } else if(s.contains("Durability")) {
                String[] split = ChatColor.stripColor(s).split("[^0-9]");
                List<String> stringList = new ArrayList<>();
                for(String str : split) if(!str.equals(" ") && !str.equals(""))stringList.add(str);
                for(String str : stringList) Bukkit.broadcastMessage(str);
                totalDurability = Integer.parseInt(stringList.get(0));
                currentDurability = Integer.parseInt(stringList.get(1));
            } else if(s.contains(getCurrentGrade(stack))) {
            }
        }
        //Bukkit.getConsoleSender().sendMessage("Original: " + Arrays.toString(x.split("\",\"")));
        Bukkit.broadcastMessage("Damage: " + damage + "\nDurability: " + totalDurability + "\\" + currentDurability);
        String rebuiltString = loreString;
        List<String> lore = stack.getItemMeta().getLore();
        rebuiltString = rebuiltString.replace(String.valueOf(damage),"50000.0");
        replaceItemInLore(lore,String.valueOf(damage),"50000.0");
        rebuiltString = rebuiltString.replace(String.valueOf(totalDurability),"1000");
        replaceItemInLore(lore,String.valueOf(totalDurability),"1000");
        rebuiltString = rebuiltString.replace(String.valueOf(currentDurability),"9000");
        replaceItemInLore(lore,String.valueOf(currentDurability),"9000");
        container.getCompound("tag").setString("MMOITEMS_LORE",rebuiltString);
        return NBTItem.convertNBTtoItem(container);
    }

    public static List<String> replaceItemInLore(List<String> lore,String replace,String replacement) {

        for(String list : lore) {
            if(list.contains(replace)) {
                list = list.replace(replace,replacement);
                if(!lore.contains(list))continue;
                lore.set(lore.indexOf(list),list);
            }
        }

        return lore;
    }
}
