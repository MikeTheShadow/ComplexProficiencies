/*
 *
 *  * Copyright 2019-2020 Michael Pape and contributors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.miketheshadow.complexproficiencies.utils.DBHandlers;

import com.miketheshadow.complexproficiencies.api.DatabaseAPI;
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

    private static final MongoCollection<Document> collection = init();
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
            MongoClient mongoClient = new MongoClient(new MongoClientURI(DatabaseAPI.getDatabaseConnection().getConnectionString()));
            MongoDatabase database = mongoClient.getDatabase("ComplexProficiencies");
            return database.getCollection("Players");
        }
        return collection;
    }
}
