package com.miketheshadow.complexproficiencies.listener;


import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void playerJoinEvent(PlayerJoinEvent event) {

        //Check if player is in database
        Player player = event.getPlayer();
        UserDBHandler.checkPlayer(player);
        CustomUser user = UserDBHandler.getPlayer(player);
        int level = user.getLevelXP()[0];
        double health = user.getLastHP();
        player.setHealth(health);
        player.setLevel(level);
        player.setExp(0);
        //Warn reset
        if(player.isOp()) user.setLabor(10000);
        UserDBHandler.updatePlayer(user);
        if(ComplexProficiencies.levelConfig.getBoolean("reset") && player.isOp()) {
            player.sendMessage(ChatColor.RED + "WARNING RESET MODE IS ENABLED! PLEASE DISABLE IN THE CONFIG IF THIS WAS NOT INTENDED!");
        }
    }

    @EventHandler(priority =  EventPriority.HIGHEST)
    public void playerLeaveEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        CustomUser user = UserDBHandler.getPlayer(player);
        user.setLastHP(player.getHealth());
        UserDBHandler.updatePlayer(user);
    }
}
