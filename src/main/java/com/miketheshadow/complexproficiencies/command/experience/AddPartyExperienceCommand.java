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

package com.miketheshadow.complexproficiencies.command.experience;

import com.miketheshadow.complexproficiencies.command.ComplexCommand;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AddPartyExperienceCommand extends ComplexCommand {

    public AddPartyExperienceCommand() {
        super("addpartyexperience");
    }
    //ADD PARTY EXPERIENCE COMMAND SHOULD LOOK LIKE THIS: /addpartyexperience miketheshadow1 level 5; level text is ignored 5 is the level and should be multiplied or whatever
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if(args.length != 3) return false;

        Player target = (Bukkit.getServer().getPlayer(args[0]));
        if(target == null) {
            sender.sendMessage("Player does not exist!");
            return true;
        }
        CustomUser user = UserDBHandler.getPlayer(target);
        Bukkit.broadcastMessage("DON'T USE ADD PARTY EXPERIENCE IT'S BROKEN! USE: /addexperience!");
        try {
            int level = Integer.parseInt(args[2]);
                /*
                WORK ON THIS
                 */
            //ExperienceUtil.addPartyExperience(user,target,level,false,false);
        }
        catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Error adding experience check that values are correct!");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
