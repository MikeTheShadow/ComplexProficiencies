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
import com.miketheshadow.complexproficiencies.utils.ExperienceUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import static com.miketheshadow.complexproficiencies.ComplexProficiencies.levelConfig;

@Command
public class ComplexDataCommand extends ComplexCommand {

    public ComplexDataCommand() {
        super("cdata");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, String[] args) {

        if(args[0].equals("pjson")) {
            if(args.length != 2) {
                sender.sendMessage(ChatColor.RED + "/cdata pjson [player]");
                return true;
            }
            CustomUser user = UserDBHandler.getPlayer(Bukkit.getPlayer(args[1]));
            JSONObject jsonObject = new JSONObject();
            jsonObject.append("level",user.getLevelXP()[0]);
            jsonObject.append("xpstart",ExperienceUtil.getPlayerCurrentXP(user));
            jsonObject.append("xpend",levelConfig.getInt("levels." + user.getLevelXP()[0]));
            sender.sendMessage(jsonObject.toString());
            return true;
        }

        return false;
    }
}
