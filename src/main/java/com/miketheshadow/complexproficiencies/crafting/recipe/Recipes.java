package com.miketheshadow.complexproficiencies.crafting.recipe;

import com.miketheshadow.complexproficiencies.gui.BaseCategories;
import com.miketheshadow.complexproficiencies.utils.CustomItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Recipes
{
    public static HashMap<String,List<CustomRecipe>> armorsmithing = new HashMap<>();
    public static HashMap<String,List<CustomRecipe>> weaponsmithing = new HashMap<>();
    public static HashMap<String,List<CustomRecipe>> cooking = new HashMap<>();
    public static HashMap<String,List<CustomRecipe>> fishing = new HashMap<>();
    public static HashMap<String,List<CustomRecipe>> leatherworking = new HashMap<>();
    public static HashMap<String,List<CustomRecipe>> mining = new HashMap<>();

    public static HashMap<String, List<CustomRecipe>> fakeRecipe()
    {
        HashMap<String, List<CustomRecipe>> testRecipe = new HashMap<>();
        List<CustomItem> itemList = new ArrayList<>();
        itemList.add(new CustomItem(16,"METALLURGY_BRONZE_INGOT",""));
        itemList.add(new CustomItem(1,"METALLURGY_KALENDRITE_INGOT",""));
        itemList.add(new CustomItem(4,"STICK",""));
        CustomRecipe recipe = new CustomRecipe(itemList,new CustomItem(1,"METALLURGY_BRONZE_SWORD","Bronze Sword"));
        List<CustomRecipe> crep = new ArrayList<>();
        crep.add(recipe);
        testRecipe.put(BaseCategories.SWORD.getItemMeta().getDisplayName(),crep);
        return testRecipe;
    }

}
