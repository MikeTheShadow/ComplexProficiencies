package com.miketheshadow.complexproficiencies.utils;

import com.miketheshadow.complexproficiencies.crafting.recipe.CustomRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class CustomItem implements Serializable
{
    public int amount;
    public String typeName;
    public String name;

    public CustomItem(int amount, String typeName, String name)
    {
        this.amount = amount;
        this.typeName = typeName;
        this.name = name;
    }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ItemStack toItem()
    {
        ItemStack stack = new ItemStack(Material.valueOf(typeName),amount);
        if(!name.equals(""))
        {
            stack.getItemMeta().setDisplayName(name);
        }
        return stack;
    }
    public static CustomItem toCustom(ItemStack stack)
    {
        String name = stack.getType().toString();
        if(stack.getItemMeta() != null)
        {
            name = stack.getItemMeta().getDisplayName();
        }
        if(name == null)
        {
            name = "";
        }
        return new CustomItem(1,stack.getType().toString(),name);
    }
    public static CustomItem toStackCustom(ItemStack stack)
    {
        String name = stack.getType().toString();
        if(stack.getItemMeta() != null)
        {
            name = stack.getItemMeta().getDisplayName();
        }
        if(name == null)
        {
            name = "";
        }
        return new CustomItem(stack.getAmount(),stack.getType().toString(),name);
    }

}
