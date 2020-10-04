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

import com.miketheshadow.complexproficiencies.api.UserAPI;
import com.miketheshadow.complexproficiencies.command.ComplexCommand;
import com.miketheshadow.complexproficiencies.command.Command;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Command
public class ComplexAPICommand extends ComplexCommand {

    public ComplexAPICommand() {
        super("capi");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, String[] args) {

        if(args[0].equals("spendlabor") || args[0].equals("sl")) {
            if(args.length != 4) {
                sender.sendMessage(ChatColor.RED + "/capi spendlabor [player] [prof] [amount]");
                return true;
            }
            Player player = Bukkit.getServer().getPlayer(args[1]);
            if(player == null) {
                sender.sendMessage(ChatColor.RED + "Player doesn't exist or isn't online!");
                return true;
            }
            try {
                if(Integer.parseInt(args[3]) < 1) {
                    throw new IllegalArgumentException("no");
                }
            } catch (Exception e) {
                if(e instanceof IllegalArgumentException) sender.sendMessage("Cannot use negative numbers for professions!");
                else sender.sendMessage("Unable to parse amount of type " + args[3]);
            }
            UserAPI.addExperienceToProf(player,args[2],Integer.parseInt(args[3]));
            return true;
        }
        if(args[0].equals("getlabor") || args[0].equals("gl")) {
            if(args.length != 2) {
                sender.sendMessage("/getlabor [player]");
                return true;
            }
            Player player = Bukkit.getPlayer(args[1]);
            if(player == null) {
                sender.sendMessage("Cannot find player: " + args[1]);
                return true;
            }
            sender.sendMessage("" + UserDBHandler.getPlayer(player).getLabor());
            return true;
        }

        sender.sendMessage("Current available api commands [getlabor], [spendlabor]");
        return true;
    }
}
