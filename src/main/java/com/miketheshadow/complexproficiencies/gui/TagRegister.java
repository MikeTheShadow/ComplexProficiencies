package com.miketheshadow.complexproficiencies.gui;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TagRegister {

    public static ItemStack previousPage = registerLeftRight(Material.GREEN_SHULKER_BOX.toString(), "PREVIOUS PAGE");
    public static ItemStack nextPage = registerLeftRight(Material.GREEN_SHULKER_BOX.toString(), "NEXT PAGE");


    public static ItemStack registerLeftRight(String itemStack, String name) {
        List<String> list = new ArrayList<>();
        list.add("Click to go to the " + name.toLowerCase());

        //create the item
        ItemStack item = new ItemStack(Material.valueOf(itemStack));
        //add tags
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + name);
        meta.setLore(list);
        item.setItemMeta(meta);

        return item;
    }
    public static ItemStack registerCrafting(String itemStack, String name, String location) {

        //create the item
        ItemStack item = new ItemStack(Material.valueOf(itemStack));

        //modify tags
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + name);
        item.setItemMeta(meta);

        //adding custom tags
        NBTContainer nbtItem = NBTItem.convertItemtoNBT(item);
        nbtItem.getCompound("tag").getCompound("display").setString("path","/" + location);
        //gui type
        nbtItem.getCompound("tag").getCompound("display").setString("type","crafting");
        item = NBTItem.convertNBTtoItem(nbtItem);
        return item;
    }
    public static ItemStack registerBuilder(String location, String name) {

        //create the item
        ItemStack item = new ItemStack(Material.valueOf(Material.RED_SHULKER_BOX.toString()));

        //modify tags
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "ADD ITEM");
        item.setItemMeta(meta);

        //adding custom tags
        NBTContainer nbtItem = NBTItem.convertItemtoNBT(item);
        nbtItem.getCompound("tag").getCompound("display").setString("title","");
        nbtItem.getCompound("tag").getCompound("display").setString("icon","");
        nbtItem.getCompound("tag").getCompound("display").setString("path","/" + location.toLowerCase());
        nbtItem.getCompound("tag").getCompound("display").setString("location","");
        //gui type
        nbtItem.getCompound("tag").getCompound("display").setString("type",name);
        item = NBTItem.convertNBTtoItem(nbtItem);
        return item;
    }
    public static ItemStack registerRecipeBuilder(String location, String name) {

        //create the item
        ItemStack item = new ItemStack(Material.valueOf(Material.RED_SHULKER_BOX.toString()));

        //modify tags
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "ADD ITEM");
        item.setItemMeta(meta);

        //adding custom tags
        NBTContainer nbtItem = NBTItem.convertItemtoNBT(item);
        nbtItem.getCompound("tag").getCompound("display").setString("title","");
        nbtItem.getCompound("tag").getCompound("display").setString("icon","");
        nbtItem.getCompound("tag").getCompound("display").setString("path","/" + location.toLowerCase());
        nbtItem.getCompound("tag").getCompound("display").setString("location","");
        //gui type
        nbtItem.getCompound("tag").getCompound("display").setString("type",name);
        item = NBTItem.convertNBTtoItem(nbtItem);
        return item;
    }

}
