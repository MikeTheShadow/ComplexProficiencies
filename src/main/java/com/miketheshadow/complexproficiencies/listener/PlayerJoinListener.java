package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.Database.CustomUser;
import com.miketheshadow.complexproficiencies.Database.DBHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener
{
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event)
    {
        for (String prof: ComplexProficiencies.profList)
        {
            Player player = event.getPlayer();
            //Add player to database if they don't exist
            CustomUser user = DBHandler.getUserByID(player.getUniqueId().toString(),prof);
            if(user == null)
            { DBHandler.insertNewUser(player.getName(),player.getUniqueId().toString(),1,0,0,prof); }
            else {break;}
        }

    }
}
