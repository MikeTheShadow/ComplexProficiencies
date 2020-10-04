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

import com.miketheshadow.complexproficiencies.command.ComplexCommand;
import com.miketheshadow.complexproficiencies.command.Command;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Command
public class ProfCommand extends ComplexCommand {

    public ProfCommand() {
        super("prof");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, String[] args)
    {
        if(args.length == 1) {
            if(!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            CustomUser user = UserDBHandler.getPlayer(player);
            if(user.getProfessions().get(args[0]) == null) {
                sender.sendMessage(ChatColor.RED + "That prof doesn't exist!");
                return true;
            }
            int level = user.getLevelFromTotal(user.getProfessions().get(args[0].toLowerCase()));
            player.sendMessage(ChatColor.GOLD + "You are level: " + ChatColor.GREEN + level + ChatColor.GOLD +" in " + ChatColor.DARK_PURPLE + args[0]);
            return true;
        } else if(args.length == 2) {
            Player player = Bukkit.getPlayer(args[1]);
            CustomUser user = UserDBHandler.getPlayer(player);
            if(user.getProfessions().get(args[0]) == null) {
                sender.sendMessage(ChatColor.RED + "That prof doesn't exist!");
                return true;
            }
            int level = user.getLevelFromTotal(user.getProfessions().get(args[0].toLowerCase()));
            sender.sendMessage(ChatColor.GOLD + "They are level: " + ChatColor.GREEN + level + ChatColor.GOLD +" in " + ChatColor.DARK_PURPLE + args[0]);
            return true;
        }
        return false;
    }
}
