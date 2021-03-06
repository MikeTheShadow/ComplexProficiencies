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
import com.miketheshadow.complexproficiencies.caravan.Caravan;
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
public class CaravanCreateCommand extends ComplexCommand {
    public CaravanCreateCommand() {
        super("caravancreate");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, String[] args)
    {
            if(args.length != 3) return false;
            Player player = Bukkit.getPlayer(args[2]);
            if(player == null) return false;
            CustomUser user = UserDBHandler.getPlayer(player);
            int moneyCost = Integer.parseInt(args[0]);
            String zoneName = args[1];
            if(!ComplexProficiencies.econ.hasAccount(player)) {
                player.sendMessage(ChatColor.RED + "You don't have enough money!");
                return true;
            } else if(ComplexProficiencies.econ.getBalance(player) < moneyCost) {
                player.sendMessage(ChatColor.RED + "You don't have enough money!");
                return true;
            }
            ComplexProficiencies.econ.withdrawPlayer(player,moneyCost);
            user.addExperience("commerce",Caravan.CRAFTING_COST,player);
            UserDBHandler.updatePlayer(user);
            Caravan.createCaravan(player,zoneName,String.valueOf(moneyCost));
            return true;
    }
}
