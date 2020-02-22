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
    private List<String> requiredItems;
    private String nbtRecipe;
    private String parent;
    private int levelReq;
    private int xpGain;

    public CustomRecipe(List<String> requiredItems, String nbtRecipe, String parent, int levelReq, int xpGain)
    {
        this.requiredItems = requiredItems;
        this.nbtRecipe = nbtRecipe;
        this.parent = parent;
        this.levelReq = levelReq;
        this.xpGain = xpGain;
    }

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


    public CustomRecipe(List<ItemStack> requiredItems, NBTContainer itemToBeCrafted,int levelReq,int xpGain)
    {
        List<String> items = new ArrayList<>();
        for (ItemStack item: requiredItems)
        {
            items.add(NBTItem.convertItemtoNBT(item).toString());
        }
        this.requiredItems = items;
        this.nbtRecipe = itemToBeCrafted.toString();
        this.levelReq = levelReq;
    }

    public int getLevelReq() { return levelReq; }
    public void setLevelReq(int levelReq) { this.levelReq = levelReq; }
    public int getXpGain() { return xpGain; }
    public void setXpGain(int xpGain) { this.xpGain = xpGain; }
    public String getParent() { return parent; }
    public void setParent(String parent) { this.parent = parent; }
}
