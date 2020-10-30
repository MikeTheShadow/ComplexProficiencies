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

package com.miketheshadow.complexproficiencies.utils;

import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class LaborThread implements Runnable
{

    Thread thread;
    String threadName;
    public static int regenAmount = 40;
    public static int MAX_LABOR = 5000;
    public static String MAX_LABOR_STRING = "5000";

    @Override
    public void run() {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Doing labor tick");
            List<CustomUser> players = UserDBHandler.getAllPlayers();
            for (CustomUser user : players) {
                int labor = user.getLabor();
                if(!(labor > MAX_LABOR - regenAmount)){
                    Player player = Bukkit.getPlayer(user.getName());
                    if(player != null && player.isOnline()) {
                        player.sendMessage(ChatColor.YELLOW + "You gained "
                                + ChatColor.GOLD
                                + regenAmount
                                + ChatColor.YELLOW
                                + " labor!"
                                + ChatColor.GRAY + " [" + ChatColor.GOLD + (labor + regenAmount) + ChatColor.GRAY + "/" + ChatColor.GOLD + MAX_LABOR_STRING + ChatColor.GRAY + "]");
                    }
                    user.setLabor(labor + regenAmount);
                    UserDBHandler.updatePlayer(user);
                }
                else if(labor < MAX_LABOR) {
                    Player player = Bukkit.getPlayer(user.getName());
                    if(player != null && player.isOnline()) {
                        player.sendMessage(ChatColor.YELLOW + "You gained "
                                + ChatColor.GOLD
                                + regenAmount
                                + ChatColor.YELLOW
                                + " labor! "
                                + ChatColor.GRAY + "[" + ChatColor.GOLD + MAX_LABOR + ChatColor.GRAY + "/" + ChatColor.GOLD + MAX_LABOR_STRING + ChatColor.GRAY + "]");
                    }
                    user.setLabor(MAX_LABOR);
                    UserDBHandler.updatePlayer(user);
                }
            }
    }


    public void start(String threadName) {

        this.threadName = threadName;
        if (this.thread == null) {
            this.thread = new Thread(this, this.threadName);
            this.thread.start();
        }

    }
}
