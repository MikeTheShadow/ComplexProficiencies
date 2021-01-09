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
