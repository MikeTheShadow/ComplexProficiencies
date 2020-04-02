package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.ExperienceUtil;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class PlayerXPListener implements Listener {


    @EventHandler
    public void playerGainsXPEvent(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        CustomUser user = UserDBHandler.getPlayer(player);
        //update user information
        ExperienceUtil.addPlayerExperience(user,player,event.getAmount(),false);
        event.setAmount(0);
        player.setExp(0);
    }
}
