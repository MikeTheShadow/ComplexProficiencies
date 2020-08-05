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

import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@ICommand
public class RemovePlayerCommand extends ComplexCommand {

    public RemovePlayerCommand() {
        super("cremoveplayer");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        if(args.length != 1) return false;
        OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
        long code = UserDBHandler.removePlayer(UserDBHandler.getPlayer(player));
        sender.sendMessage(ChatColor.GREEN + "Removed player '" + player.getName() + "' level data with code: " + code);
        return true;
    }

}
