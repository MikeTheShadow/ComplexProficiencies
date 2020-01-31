package com.miketheshadow.complexproficiencies.crafting.recipe;

import java.io.Serializable;
import java.util.List;

public class CustomRecipe implements Serializable
{
    List<CustomItem> requiredItems;
    CustomItem itemToBeCrafted;

    public List<CustomItem> getRequiredItems() { return requiredItems; }
    public void setRequiredItems(List<CustomItem> requiredItems) { this.requiredItems = requiredItems; }
    public CustomItem getItemToBeCrafted() { return itemToBeCrafted; }
    public void setItemToBeCrafted(CustomItem itemToBeCrafted) { this.itemToBeCrafted = itemToBeCrafted; }

    public CustomRecipe(List<CustomItem> requiredItems, CustomItem itemToBeCrafted)
    {
        this.requiredItems = requiredItems;
        this.itemToBeCrafted = itemToBeCrafted;
    }

}
