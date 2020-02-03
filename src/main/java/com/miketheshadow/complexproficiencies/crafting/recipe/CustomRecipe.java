package com.miketheshadow.complexproficiencies.crafting.recipe;

import com.miketheshadow.complexproficiencies.utils.CustomItem;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;

import java.io.Serializable;
import java.util.List;

public class CustomRecipe implements Serializable
{
    public List<CustomItem> requiredItems;
    public NBTCompound itemToBeCrafted;
    public List<CustomItem> getRequiredItems() { return requiredItems; }
    public void setRequiredItems(List<CustomItem> requiredItems) { this.requiredItems = requiredItems; }
    public NBTCompound getItemToBeCrafted() { return itemToBeCrafted; }
    public void setItemToBeCrafted(NBTItem itemToBeCrafted) { this.itemToBeCrafted = itemToBeCrafted; }

    public CustomRecipe(List<CustomItem> requiredItems, NBTCompound itemToBeCrafted)
    {
        this.requiredItems = requiredItems;
        this.itemToBeCrafted = itemToBeCrafted;
    }

}
