package com.miketheshadow.complexproficiencies.utils.DBHandlers;

import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class UserDBHandler {

    private static  MongoCollection<Document> collection = init();
    public static void checkPlayer(Player player) {
        FindIterable<Document> cursor = collection.find(new BasicDBObject("uid", player.getUniqueId().toString()));
        if (cursor.first() == null) {
            CustomUser customPlayer = new CustomUser(player.getName(), player.getUniqueId().toString());
            customPlayer.setLabor(1000);
            collection.insertOne(customPlayer.toDocument());
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Adding new player: " + player.getName());
            if(!player.isOp()) {
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
                player.getInventory().setItemInOffHand(null);
                player.getEnderChest().clear();
            }

        }
    }

    public static CustomUser getPlayer(String name) {
        FindIterable<Document> cursor = collection.find(new BasicDBObject("name",name));
        return new CustomUser(Objects.requireNonNull(cursor.first()));
    }

    public static CustomUser getPlayer(Player player) {
        FindIterable<Document> cursor = collection.find(new BasicDBObject("uid", player.getUniqueId().toString()));
        return new CustomUser(Objects.requireNonNull(cursor.first()));
    }
    public static CustomUser getPlayer(OfflinePlayer player) {
        FindIterable<Document> cursor = collection.find(new BasicDBObject("uid", player.getUniqueId().toString()));
        return new CustomUser(Objects.requireNonNull(cursor.first()));
    }
    public static void updatePlayer(CustomUser player) {
        collection.replaceOne(new BasicDBObject("uid", player.getUid()), player.toDocument());
    }

    public static long removePlayer(CustomUser player) {
        DeleteResult result = collection.deleteOne(new BasicDBObject("uid", player.getUid()));
        return result.getDeletedCount();
    }
    public static List<CustomUser> getAllPlayers() {
        List<CustomUser> users = new ArrayList<>();
        for (Document document: collection.find()) {
            users.add(new CustomUser(document));
        }
        return users;
    }

    //for fixing documents
    public static List<Document> getAllDocuments() {
        List<Document> documents = new ArrayList<>();
        for (Document document: collection.find()) {
            documents.add(document);
        }
        return documents;
    }
    public static void updateDocument(Document document) {
        collection.replaceOne(new BasicDBObject("uid",document.get("uid")),document);
    }


    public static MongoCollection<Document> init() {
        if(collection == null) {
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            MongoDatabase database = mongoClient.getDatabase("ComplexProficiencies");
            return database.getCollection("Players");
        }
        return collection;
    }
}
