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

package com.miketheshadow.complexproficiencies.command.base;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.command.ComplexCommand;
import com.miketheshadow.complexproficiencies.command.Command;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command
public class ResetDBCommand extends ComplexCommand
{

    private static boolean active = false;

    public ResetDBCommand() {
        super("resetdb");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, String[] args)
    {
        if(cmd.getName().equalsIgnoreCase("resetdb")) {
            if(!active && ComplexProficiencies.levelConfig.getBoolean("reset")) {
                active = true;
                Bukkit.broadcastMessage(ChatColor.RED + "DELETING ALL USER DATA IN COMPLEX PROFICIENCIES in 30s RUN THIS COMMAND AGAIN TO CANCEL!");
                Bukkit.getScheduler().scheduleSyncDelayedTask(ComplexProficiencies.INSTANCE, () -> {
                    if(active) {
                        Bukkit.broadcastMessage(ChatColor.RED + "REMOVING PLAYER DATA...");
                        List<CustomUser> userList = UserDBHandler.getAllPlayers();
                        for (CustomUser user: userList) {
                            UserDBHandler.removePlayer(user);
                        }
                        Bukkit.broadcastMessage(ChatColor.RED + "COMPLETE!");
                    }
                }, 600);
            } else if(active) {
                Bukkit.broadcastMessage(ChatColor.RED + "DEACTIVATING DATA REMOVAL!");
                active = false;
            } else {
                sender.sendMessage(ChatColor.RED + "Reset is disabled!");
            }
            return true;
        }
        return false;
    }
}
