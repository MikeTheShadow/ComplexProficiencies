package com.miketheshadow.complexproficiencies.utils.DBHandlers;

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

    private static  MongoCollection<Document> collection = init();
    public static boolean checkCategory(Category category) {
        FindIterable<Document> cursor = collection.find(new BasicDBObject("path", category.getPath()));
        if (cursor.first() == null) {
            collection.insertOne(category.toDocument());
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Adding new category: " + category.getTitle());
            return true;
        }
        return false;
    }
    public static boolean addSubCategory(Category category) {
        FindIterable<Document> cursor = collection.find(new BasicDBObject("path", category.getPath()));
        if (cursor.first() == null) {
            collection.insertOne(category.toDocument());
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Adding new category: " + category.getTitle());
            return true;
        }
        else
        {
            Bukkit.broadcastMessage(cursor.first().toJson());
        }
        return false;
    }
    public static List<Category> getSubCategories(String location) {
        FindIterable<Document> cursor = collection.find(new BasicDBObject("location", location));
        List<Category> categories = new ArrayList<>();
        for (Document document : cursor) {
            categories.add(new Category(document));
        }
        return categories;
    }
    public static Category getCategory(String path){
        FindIterable<Document> cursor = collection.find(new BasicDBObject("path", path));
        if(cursor.first() == null) return null;
        return new Category(cursor.first());
    }
    public static void updateCategory(Category category) {
        collection.replaceOne(new BasicDBObject("path", category.getPath()), category.toDocument());
    }

    public static boolean removeCategory(Category category){
        FindIterable<Document> cursor = collection.find(new BasicDBObject("path", category.getPath()));
        Document remove = cursor.first();
        if(remove == null) return false;
        collection.deleteOne(remove);
        return true;
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

    public static List<Document> getAllDocuments() {
        return null;
    }

    public static void updateDocument(Document document) {

    }
}
