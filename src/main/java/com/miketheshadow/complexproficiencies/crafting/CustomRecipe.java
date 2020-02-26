package com.miketheshadow.complexproficiencies.crafting;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import org.bson.Document;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomRecipe implements Serializable {
    private List<String> requiredItems;
    private String nbtRecipe;
    private String parent;
    private int levelReq;
    private int xpGain;
    private String proffesion;

    public CustomRecipe(List<ItemStack> requiredItems, NBTContainer itemToBeCrafted, int levelReq, int xpGain, String proffesion) {
        List<String> items = new ArrayList<>();
        for (ItemStack item : requiredItems) {
            items.add(NBTItem.convertItemtoNBT(item).toString());
        }
        this.requiredItems = items;
        this.nbtRecipe = itemToBeCrafted.toString();
        this.levelReq = levelReq;
        this.xpGain = xpGain;
        this.proffesion = proffesion;
    }

    public CustomRecipe(Document document) {
        this.nbtRecipe = document.getString("item");
        this.requiredItems = document.getList("ingredients", String.class);
        this.parent = document.getString("parent");
        this.levelReq = document.getInteger("levelReq");
        this.xpGain = document.getInteger("xpGain");
        this.proffesion = document.getString("profession");
    }

    public List<ItemStack> getRequiredItems() {
        List<ItemStack> items = new ArrayList<>();
        for (String item : requiredItems) {
            items.add(NBTItem.convertNBTtoItem(new NBTContainer(item)));
        }
        return items;
    }

    public void setRequiredItems(List<ItemStack> requiredItems) {
        List<String> items = new ArrayList<>();
        for (ItemStack item : requiredItems) {
            items.add(NBTItem.convertItemtoNBT(item).toString());
        }
        this.requiredItems = items;
    }

    public NBTCompound getItemToBeCrafted() {
        return new NBTContainer(nbtRecipe);
    }

    public void setItemToBeCrafted(NBTItem itemToBeCrafted) {
        this.nbtRecipe = itemToBeCrafted.toString();
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("ingredients", requiredItems);
        document.append("item", nbtRecipe);
        document.append("parent", parent);
        document.append("levelReq", levelReq);
        document.append("xpGain", xpGain);
        document.append("profession", proffesion);
        return document;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public void setLevelReq(int levelReq) {
        this.levelReq = levelReq;
    }

    public int getXpGain() {
        return xpGain;
    }

    public void setXpGain(int xpGain) {
        this.xpGain = xpGain;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
