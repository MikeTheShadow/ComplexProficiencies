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
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Command
public class ResetLaborCommand extends ComplexCommand {

    private final List<String> names = new ArrayList<>();

    public ResetLaborCommand() {
        super("claborreset");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, String[] args) {
        if(names.contains(sender.getName())) {
            names.remove(sender.getName());
            for(CustomUser user : UserDBHandler.getAllPlayers()) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(user.getUid()));
                if(player.isOp()) continue;
                UserDBHandler.updatePlayer(user);
            }
            sender.sendMessage(ChatColor.GREEN + "You've reset all player labor!");
        }
        else if(sender.isOp()) {
            names.add(sender.getName());
            sender.sendMessage(ChatColor.RED + "Run this command again to reset labor!");
        }
        return true;
    }
}
