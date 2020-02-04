package com.miketheshadow.complexproficiencies.crafting.recipe;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomRecipe implements Serializable
{
    public List<String> requiredItems;
    public String nbtRecipe;

    public List<ItemStack> getRequiredItems()
    {
        List<ItemStack> items = new ArrayList<>();
        for (String item: requiredItems)
        {
            items.add(NBTItem.convertNBTtoItem(new NBTContainer(item)));
        }
        return items;
    }
    public void setRequiredItems(List<ItemStack> requiredItems)
    {
        List<String> items = new ArrayList<>();
        for (ItemStack item: requiredItems)
        {
            items.add(NBTItem.convertItemtoNBT(item).toString());
        }
        this.requiredItems = items;
    }
    public NBTCompound getItemToBeCrafted() { return new NBTContainer(nbtRecipe); }


    public void setItemToBeCrafted(NBTItem itemToBeCrafted) { this.nbtRecipe = itemToBeCrafted.toString(); }
    public CustomRecipe(List<ItemStack> requiredItems, NBTContainer itemToBeCrafted)
    {
        List<String> items = new ArrayList<>();
        for (ItemStack item: requiredItems)
        {
            items.add(NBTItem.convertItemtoNBT(item).toString());
        }
        this.requiredItems = items;
        this.nbtRecipe = itemToBeCrafted.toString();
    }

}
