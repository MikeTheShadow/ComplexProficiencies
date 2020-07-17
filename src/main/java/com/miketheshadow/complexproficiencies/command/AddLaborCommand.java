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

package com.miketheshadow.complexproficiencies.command;

import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import com.miketheshadow.complexproficiencies.utils.LaborThread;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AddLaborCommand extends ComplexCommand {


    public AddLaborCommand() {
        super("caddlabor");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        if(args.length != 2) return false;
        Player player = Bukkit.getPlayer(args[0]);
        int amount = Integer.parseInt(args[1]);
        if(player == null) {
            sender.sendMessage("Player does not exist!");
            return true;
        } else {
           CustomUser user = UserDBHandler.getPlayer(player);
           if(user.getLabor() + amount > LaborThread.cap) {
               player.sendMessage(ChatColor.RED + "You cannot go over your labor cap!");
           } else {
               user.setLabor(user.getLabor() + amount);
               if(user.getLabor() < 0) user.setLabor(0);
               player.sendMessage(ChatColor.YELLOW + "You gained "
                       + ChatColor.GOLD
                       + amount
                       + ChatColor.YELLOW
                       + " labor! "
                       + ChatColor.GRAY + "[" + ChatColor.GOLD + (user.getLabor()+amount) + ChatColor.GRAY + "/" + ChatColor.GOLD + LaborThread.cap + ChatColor.GRAY + "]");
               UserDBHandler.updatePlayer(user);
           }
        }
        return true;
    }
}
