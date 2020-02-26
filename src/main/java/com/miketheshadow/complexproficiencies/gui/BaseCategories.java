package com.miketheshadow.complexproficiencies.gui;

import com.miketheshadow.complexproficiencies.crafting.CustomRecipe;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BaseCategories {
    //ez copy paste keep commented dumbass
    //public static final ItemStack BASIC = register("","");


    //register basic weapons
    public static final ItemStack SWORD = register("TCONSTRUCT_BROADSWORD", "SWORDS");
    public static final ItemStack GREATSWORD = register("TCONSTRUCT_CLEAVER", "GREATSWORDS");
    public static final ItemStack RAPIER = register("TCONSTRUCT_RAPIER", "RAPIERS");
    public static final ItemStack DAGGER = register("METALLURGY_SILVER_SWORD", "DAGGERS");
    public static final ItemStack AXE = register("TCONSTRUCT_HATCHET", "AXES");
    public static final ItemStack HAMMER = register("TCONSTRUCT_HAMMER", "HAMMERS");
    public static final ItemStack SPEAR = register("TCONSTRUCT_ARROW_HEAD", "SPEARS");

    //register basic cooking
    public static final ItemStack POTION = register("POTION", "POTIONS");
    public static final ItemStack FOOD = register("COOKED_BEEF", "HEALTH BUFF FOOD");
    public static final ItemStack SOUP = register("MUSHROOM_SOUP", "HEALTH REGEN FOOD");
    public static final ItemStack RAREFOOD = register("GOLDEN_APPLE", "RARE RECIPES");

    //register fishing
    public static final ItemStack RODS = register("FISHING_ROD", "RODS");
    public static final ItemStack OILS = register("HARVESTCRAFT_OLIVEOILITEM", "OILS");
    public static final ItemStack PIGMENTS = register("INK_SACK", "PIGMENTS");
    public static final ItemStack POLISH = register("FAIRYLIGHTS_LIGHT", "POLISH");

    //register handicrafts -> being moved to fishing
    //public static final ItemStack RINGS = register("BAUBLES_RING","RINGS");
    //public static final ItemStack NECKLACES = register("ARTIFACTS_PANIC_NECKLACE","AMULETS");
    //public static final ItemStack CHARMS = register("GRIMOIREOFGAIA_ACCESSORY_TRINKET_POISON","CHARMS");
    //public static final ItemStack BELT = register("GOLD_NUGGET","BELT");
    //public static final ItemStack HEAD = register("GOLD_NUGGET","HEAD");
    //public static final ItemStack BODY = register("GOLD_NUGGET","BODY");

    //register leatherworking
    public static final ItemStack LEATHERGOODS = register("LEATHER", "LEATHER GOODS");
    public static final ItemStack LEATHER_HELMETS = register("LEATHER_HELMET", "CAPS");
    public static final ItemStack LEATHER_CHESTPLATE = register("LEATHER_CHESTPLATE", "JERKINS");
    public static final ItemStack LEATHER_LEGGINGS = register("LEATHER_LEGGINGS", "BREECHES");
    public static final ItemStack LEATHER_BOOTS = register("LEATHER_BOOTS", "BOOTS");

    //register metalworking
    public static final ItemStack METALS = register("IRON_INGOT", "METALS");
    public static final ItemStack PICKAXES = register("METALLURGY_SHADOW_STEEL_PICKAXE", "PICKAXES");
    public static final ItemStack HOES = register("METALLURGY_SHADOW_STEEL_HOE", "HOES");
    public static final ItemStack SHOVELS = register("METALLURGY_SHADOW_STEEL_SHOVEL", "SHOVELS");
    public static final ItemStack AXES = register("METALLURGY_SHADOW_STEEL_AXE", "AXES");

    //register armorsmithing
    public static final ItemStack HELMET = register("METALLURGY_SHADOW_IRON_HELMET", "HELMS");
    public static final ItemStack CUIRASS = register("METALLURGY_SHADOW_IRON_CHESTPLATE", "CUIRASS");
    public static final ItemStack GREAVES = register("METALLURGY_SHADOW_IRON_LEGGINGS", "GREAVES");
    public static final ItemStack SABATONS = register("METALLURGY_SHADOW_IRON_BOOTS", "SABATONS");

    public static List<ItemStack> armorsmithingItems() {
        List<ItemStack> itemStack = new ArrayList<>();
        itemStack.add(HELMET);
        itemStack.add(CUIRASS);
        itemStack.add(GREAVES);
        itemStack.add(SABATONS);
        return itemStack;
    }

    public static List<ItemStack> cookingItems() {
        List<ItemStack> itemStack = new ArrayList<>();
        itemStack.add(POTION);
        itemStack.add(FOOD);
        itemStack.add(SOUP);
        itemStack.add(RAREFOOD);

        return itemStack;
    }

    public static List<ItemStack> fishingItems() {
        List<ItemStack> itemStack = new ArrayList<>();
        itemStack.add(RODS);
        itemStack.add(OILS);
        itemStack.add(PIGMENTS);
        itemStack.add(POLISH);
        return itemStack;
    }

    public static List<ItemStack> handicraftItems() {
        List<ItemStack> itemStack = new ArrayList<>();
        //itemStack.add(RODS);
        //itemStack.add(RINGS);
        //itemStack.add(NECKLACES);
        //itemStack.add(CHARMS);
        //itemStack.add(BELT);
        //itemStack.add(HEAD);
        //itemStack.add(BODY);
        return itemStack;
    }

    public static List<ItemStack> leatherworkingItems() {
        List<ItemStack> itemStack = new ArrayList<>();
        itemStack.add(LEATHERGOODS);
        itemStack.add(LEATHER_HELMETS);
        itemStack.add(LEATHER_CHESTPLATE);
        itemStack.add(LEATHER_LEGGINGS);
        itemStack.add(LEATHER_BOOTS);
        return itemStack;
    }

    public static List<ItemStack> metalworkingItems() {
        List<ItemStack> itemStack = new ArrayList<>();
        itemStack.add(METALS);
        itemStack.add(PICKAXES);
        itemStack.add(HOES);
        itemStack.add(SHOVELS);
        itemStack.add(AXES);
        return itemStack;
    }

    public static List<ItemStack> weaponsmithingItems() {
        List<ItemStack> itemStack = new ArrayList<>();
        itemStack.add(SWORD);
        itemStack.add(GREATSWORD);
        itemStack.add(RAPIER);
        itemStack.add(DAGGER);
        itemStack.add(AXE);
        itemStack.add(HAMMER);
        itemStack.add(SPEAR);
        return itemStack;
    }

    public static List<ItemStack> getAllItems() {
        List<ItemStack> allItems = new ArrayList<>(weaponsmithingItems());
        allItems.addAll(cookingItems());
        allItems.addAll(fishingItems());
        allItems.addAll(handicraftItems());
        allItems.addAll(leatherworkingItems());
        allItems.addAll(metalworkingItems());
        allItems.addAll(armorsmithingItems());
        return allItems;
    }

    public static ItemStack register(String itemStack, String name) {
        List<String> list = new ArrayList<>();
        list.add("Click to open the " + name.toLowerCase() + " menu.");

        //create the item
        ItemStack item = new ItemStack(Material.valueOf(itemStack));
        NBTContainer nbtItem = NBTItem.convertItemtoNBT(item);
        nbtItem.setString("Name", ChatColor.GREEN + name);
        item = NBTItem.convertNBTtoItem(nbtItem);
        //add the rest of the tags
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + name);
        meta.setLore(list);
        item.setItemMeta(meta);


        List<CustomRecipe> crep = new ArrayList<>();
        //Recipes.recipes.putIfAbsent(meta.getDisplayName(), crep);
        return item;
    }
}
