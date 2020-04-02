package com.miketheshadow.complexproficiencies.utils.DBHandlers;

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

public class RecipeDBHandler {

    private static  MongoCollection<Document> collection = init();
    public static void checkRecipe(CustomRecipe recipe) {
        FindIterable<Document> cursor = collection.find(new BasicDBObject("name", recipe.getName()));
        try {
            if (cursor.first() == null) {
                collection.insertOne(recipe.toDocument());
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Adding new item: " + recipe.getItemToBeCrafted());
            }
        } catch (Exception e) {
            collection.insertOne(recipe.toDocument());
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Adding new item: " + recipe.getItemToBeCrafted());
        }
    }

    public static List<CustomRecipe> getRecipesByParent(String recipe) {
        FindIterable<Document> cursor = collection.find(new BasicDBObject("parent", recipe));
        List<CustomRecipe> recipes = new ArrayList<>();
        for (Document document : cursor) {
            recipes.add(new CustomRecipe(document));
        }
        return recipes;
    }
    public static CustomRecipe getRecipeByItem(String name)
    {
        FindIterable<Document> cursor = collection.find(new BasicDBObject("name", name));
        return new CustomRecipe(cursor.first());
    }

    public static void updateRecipe(CustomRecipe recipe) {
        collection.replaceOne(new BasicDBObject("item", recipe.getItemToBeCrafted()), recipe.toDocument());
    }
    public static void removeRecipe(CustomRecipe recipe) {
        collection.deleteOne(new BasicDBObject("item", recipe.getItemToBeCrafted()));
    }
    public static MongoCollection<Document> init() {
        if(collection == null)
        {
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            MongoDatabase database = mongoClient.getDatabase("ComplexProficiencies");
            return database.getCollection("Recipes");
        }
        return collection;
    }

    public static List<Document> getAllDocuments() {
        return null;
    }

    public static void updateDocument(Document document) {

    }
}
