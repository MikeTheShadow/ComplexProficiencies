package com.miketheshadow.complexproficiencies.utils;

import com.miketheshadow.complexproficiencies.crafting.CustomRecipe;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class RecipeDBHandler
{

    public static void checkRecipe(CustomRecipe recipe)
    {
        FindIterable<Document> cursor = loadRecipes().find(new BasicDBObject("item",recipe.getItemToBeCrafted()));
        try
        {
            if(cursor.first() == null)
            {
                loadRecipes().insertOne(recipe.toDocument());
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Adding new item: " + recipe.getItemToBeCrafted());
            }
        }
        catch (Exception e)
        {
            loadRecipes().insertOne(recipe.toDocument());
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Adding new item: " + recipe.getItemToBeCrafted());
        }
    }
    public static List<CustomRecipe> getRecipesByParent(String recipe)
    {
        FindIterable<Document> cursor = loadRecipes().find(new BasicDBObject("parent",recipe));
        List<CustomRecipe> recipes = new ArrayList<>();
        for (Document document: cursor)
        {
            recipes.add(new CustomRecipe(document));
        }
        return recipes;
    }
    public static void updateRecipe(CustomRecipe recipe)
    {
        loadRecipes().replaceOne(new BasicDBObject("item",recipe.getItemToBeCrafted()),recipe.toDocument());
    }

    public static MongoCollection<Document> loadRecipes()
    {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase database = mongoClient.getDatabase("ComplexProficiencies");
        return database.getCollection("Recipes");
    }
}
