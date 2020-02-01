package com.miketheshadow.complexproficiencies.crafting;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.crafting.recipe.Crafter;
import com.miketheshadow.complexproficiencies.crafting.recipe.CustomItem;
import com.miketheshadow.complexproficiencies.crafting.recipe.CustomRecipe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WeaponsmithingGUI
{
    public static final ItemStack SWORD = register("TCONSTRUCT_BROADSWORD","SWORDS");
    public static final ItemStack GREATSWORD = register("TCONSTRUCT_CLEAVER","GREATSWORDS");
    public static final ItemStack RAPIER = register("TCONSTRUCT_RAPIER","RAPIERS");
    public static final ItemStack DAGGER = register("METALLURGY_SILVER_SWORD","DAGGERS");
    public static final ItemStack AXE = register("TCONSTRUCT_HATCHET","AXES");
    public static final ItemStack HAMMER = register("TCONSTRUCT_HAMMER","HAMMERS");
    public static final ItemStack SPEAR = register("TCONSTRUCT_ARROW_HEAD","SPEARS");
    public static List<ItemStack> weaponTypes = new ArrayList<>();
    public static List<CustomRecipe> recipes = new ArrayList<>();
    public WeaponsmithingGUI(Player player)
    {
        if(weaponTypes.size() < 1)
        {
            weaponTypes.add(SWORD);
            weaponTypes.add(GREATSWORD);
            weaponTypes.add(RAPIER);
            weaponTypes.add(DAGGER);
            weaponTypes.add(AXE);
            weaponTypes.add(HAMMER);
            weaponTypes.add(SPEAR);
        }
        //generate the options
        Inventory inventory = Bukkit.createInventory(player, 18, "Weaponsmithing");
        inventory.setItem(0,SWORD);
        inventory.setItem(1,GREATSWORD);
        inventory.setItem(2,RAPIER);
        inventory.setItem(3,DAGGER);
        inventory.setItem(4,AXE);
        inventory.setItem(5,HAMMER);
        inventory.setItem(6,SPEAR);
        player.openInventory(inventory);
        ComplexProficiencies.crafters.put(player.getUniqueId(),new Crafter(player));
    }
    public static ItemStack register(String itemStack,String name)
    {
        List<String> list = new ArrayList<>();
        list.add("Click to open the " + name.toLowerCase() + " menu.");
        ItemStack item = new ItemStack(Material.valueOf(itemStack));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + name);
        meta.setLore(list);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack register(String itemStack,String name,String menuType)
    {
        List<String> list = new ArrayList<>();
        list.add(menuType);
        ItemStack item = new ItemStack(Material.valueOf(itemStack));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + name);
        meta.setLore(list);
        item.setItemMeta(meta);
        return item;
    }

    public static void subWeaponInventory(Player player,ItemStack itemStack)
    {
        Inventory inventory = Bukkit.createInventory(player, 54, itemStack.getItemMeta().getDisplayName());
        player.openInventory(inventory);
        inventory.setItem(0,register("METALLURGY_BRONZE_SWORD","Bronze Sword","Craft Bronze Sword"));
    }

    public static void craftingInventory(Player player,CustomRecipe recipe)
    {
        ItemStack stack = new ItemStack(Material.valueOf(recipe.getItemToBeCrafted().getTypeName()));
        Crafter crafter = ComplexProficiencies.crafters.get(player.getUniqueId());
        if(crafter == null)return;
        Inventory inventory = Bukkit.createInventory(player, 54, recipe.getItemToBeCrafted().getName());
        player.openInventory(inventory);
        inventory.setItem(0,stack);
        int i = 9;
        for (CustomItem item: recipe.getRequiredItems())
        {
            inventory.setItem(i,item.toItem());
            i++;
        }
    }
    public static boolean craftItem(Player player, CustomRecipe recipe)
    {
        for (CustomItem item:recipe.getRequiredItems())
        {
            player.getInventory().removeItem(new ItemStack(Material.valueOf(item.getTypeName()),item.getAmount()));
        }
        player.getInventory().addItem(recipe.getItemToBeCrafted().toItem());
        return true;
    }

}
