package com.miketheshadow.complexproficiencies.crafting.recipe;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.gui.BaseCategories;
import com.miketheshadow.complexproficiencies.utils.CustomItem;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Recipes
{
    public static HashMap<String,List<CustomRecipe>> recipes = new HashMap<>();
    public static String location;
    public static String name = "recipes.storage";
    public static void register(String location,CustomRecipe recipe)
    {
        recipes.get(location).add(recipe);
    }


    public static void saveRecipes()
    {
        System.out.println(ChatColor.GREEN + "SAVING RECIPES");
        try
        {
            FileOutputStream fileOut = new FileOutputStream(location + name);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(recipes);
            objectOut.close();
        }
        catch (Exception ex)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "WARNING CANNOT SAVE RECIPES TO FILE!!!");
        }
    }
    public static void loadRecipes()
    {
        System.out.println(ChatColor.GREEN + "LOADING RECIPES");
        try {
            FileInputStream fileIn = new FileInputStream(location + name);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            objectIn.close();
            recipes = (HashMap<String,List<CustomRecipe>>) obj;

        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "WARNING CANNOT LOAD RECIPES FROM FILE!!!");
        }
    }

}
