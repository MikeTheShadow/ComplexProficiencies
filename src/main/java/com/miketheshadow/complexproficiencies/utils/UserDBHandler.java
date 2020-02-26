package com.miketheshadow.complexproficiencies.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;

public class UserDBHandler {

    public static  MongoCollection<Document> collection = init();
    public static void checkPlayer(Player player) {
        FindIterable<Document> cursor = collection.find(new BasicDBObject("uid", player.getUniqueId().toString()));
        if (cursor.first() == null) {
            CustomUser customPlayer = new CustomUser(player.getName(), player.getUniqueId().toString());
            collection.insertOne(customPlayer.toDocument());
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Adding new player: " + player.getName());
        }
    }

    public static CustomUser getPlayer(Player player) {
        FindIterable<Document> cursor = collection.find(new BasicDBObject("uid", player.getUniqueId().toString()));
        return new CustomUser(Objects.requireNonNull(cursor.first()));
    }

    public static void updatePlayer(CustomUser player) {
        collection.replaceOne(new BasicDBObject("uid", player.getUid()), player.toDocument());
    }

    public static MongoCollection<Document> init() {
        if(collection == null)
        {
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            MongoDatabase database = mongoClient.getDatabase("ComplexProficiencies");
            return database.getCollection("Players");
        }
        return collection;
    }
}
