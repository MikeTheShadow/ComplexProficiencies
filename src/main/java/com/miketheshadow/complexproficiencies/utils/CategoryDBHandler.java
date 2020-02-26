package com.miketheshadow.complexproficiencies.utils;

import com.miketheshadow.complexproficiencies.crafting.Category;
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

public class CategoryDBHandler {

    public static  MongoCollection<Document> collection = init();
    public static void checkCategory(Category category) {
        FindIterable<Document> cursor = collection.find(new BasicDBObject("path", category.getPath()));
        try {
            if (cursor.first() == null) {
                collection.insertOne(category.toDocument());
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Adding new category: " + category.getTitle());
            }
        } catch (Exception e) {
            collection.insertOne(category.toDocument());
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Adding new category: " + category.getTitle());
        }
    }

    public static List<Category> getSubCategories(String path) {
        FindIterable<Document> cursor = collection.find(new BasicDBObject("path", path));
        List<Category> categories = new ArrayList<>();
        for (Document document : cursor) {
            categories.add(new Category(document));
        }
        return categories;
    }

    public static void updateCategory(Category category) {
        collection.replaceOne(new BasicDBObject("path", category.getPath()), category.toDocument());
    }

    public static MongoCollection<Document> init() {
        if(collection == null)
        {
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            MongoDatabase database = mongoClient.getDatabase("ComplexProficiencies");
            return database.getCollection("Categories");
        }
        return collection;
    }

}
