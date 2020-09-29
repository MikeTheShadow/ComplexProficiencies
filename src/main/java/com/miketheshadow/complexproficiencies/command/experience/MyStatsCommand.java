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
import com.miketheshadow.complexproficiencies.command.ICommand;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import com.miketheshadow.complexproficiencies.utils.ExperienceUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.miketheshadow.complexproficiencies.ComplexProficiencies.levelConfig;

@ICommand
public class MyStatsCommand extends ComplexCommand {

    public MyStatsCommand() {
        super("mystats");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }
        if(args.length > 0)return false;
        Player player = (Player) sender;
        CustomUser user = UserDBHandler.getPlayer(player);
        StringBuilder builder = new StringBuilder();
        //open stats
        builder.append("\n").append(StringUtils.repeat("§5~",20)).append("\n");
        //add username
        builder.append(StringUtils.repeat(" ",10 - (user.getName().length()/2)));
        builder.append("§6").append(user.getName()).append("\n").append("\n \n");

        int level = user.getLevelXP()[0];

        //experience information if user is max level do not display information
        if(levelConfig.getInt("levels." + (level + 1))  != 0 ) {
            builder.append("§3Level: ").append(level).append("\n \n");
            builder.append("§2Current Experience: §a").append(ExperienceUtil.getPlayerCurrentXP(user)).append("§e/§a").append(levelConfig.getInt("levels." + level)).append("\n \n");
        }
        else {
            builder.append("§3Level: ").append(level).append("§5 (MAX)").append("\n \n");
        }
        //finally add total Experience
        builder.append("§9Total Experience: §c").append(user.getLevelXP()[1]).append("\n");
        //close stats
        builder.append(StringUtils.repeat("§5~",20));
        player.sendMessage(builder.toString());
        return true;
    }
}
