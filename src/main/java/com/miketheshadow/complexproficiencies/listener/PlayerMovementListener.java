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
