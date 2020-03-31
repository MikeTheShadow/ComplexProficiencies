package com.miketheshadow.complexproficiencies.listener;


import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.UserDBHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoinEvent(PlayerJoinEvent event) {

        //Check if player is in database
        Player player = event.getPlayer();
        UserDBHandler.checkPlayer(player);
        int level = UserDBHandler.getPlayer(player).getLevelXP()[0];
        if(player.getLevel() != level) player.setLevel(level);
        player.setExp(0);
        //Warn reset
        if(ComplexProficiencies.levelConfig.getBoolean("reset") && player.isOp()) {
            player.sendMessage(ChatColor.RED + "WARNING RESET MODE IS ENABLED! PLEASE DISABLE IN THE CONFIG IF THIS WAS NOT INTENDED!");
        }
    }


    //TODO check to see if the old hp error is still there.
    @EventHandler(priority =  EventPriority.HIGHEST)
    public void playerLeaveEvent(PlayerQuitEvent event) {
    }
}
