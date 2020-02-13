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

public class DBHandler
{
    public static MongoCollection<Document> players;
    public static void init()
    {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase database = mongoClient.getDatabase("ComplexProficiencies");
        players = database.getCollection("Players");
    }
    public static void checkPlayer(Player player)
    {
        FindIterable<Document> cursor = players.find(new BasicDBObject("name",player.getName()));
        if(cursor.first() == null)
        {
            CustomPlayer customPlayer = new CustomPlayer(player.getName(),player.getUniqueId().toString());
            players.insertOne(customPlayer.toDocument());
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Adding player" + player.getName());
        }
    }
    public static CustomPlayer getPlayer(Player player)
    {
        FindIterable<Document> cursor = players.find(new BasicDBObject("uid",player.getUniqueId().toString()));
        return new CustomPlayer(Objects.requireNonNull(cursor.first()));
    }
    public static void updatePlayer(CustomPlayer player)
    {
        players.replaceOne(new BasicDBObject("uid",player.getUid()),player.toDocument());
    }


}
