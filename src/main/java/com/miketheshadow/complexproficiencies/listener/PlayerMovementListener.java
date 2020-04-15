package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerMovementListener implements Listener {

    /*
    WIP ON PLAYER DETECTION
    NEEDS TO BE UNCOMMENTED AND REGISTERED
     */

    List<UUID> playerList = new ArrayList<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        for(Entity entity : player.getNearbyEntities(25,25,25)) {
            if(playerList.contains(player.getUniqueId()))return;
            if(entity instanceof Player) {
                Player found = (Player)entity;
                player.sendMessage(ChatColor.BOLD + "" + ChatColor.UNDERLINE +  ChatColor.YELLOW + "Someone is watching you with murderous intent....");
                playerList.add(player.getUniqueId());
                Bukkit.getScheduler().scheduleSyncDelayedTask(ComplexProficiencies.INSTANCE, () -> playerList.remove(player.getUniqueId()), 600L);
            }
        }
    }
}
