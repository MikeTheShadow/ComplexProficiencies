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
import com.miketheshadow.complexproficiencies.command.Command;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import com.miketheshadow.complexproficiencies.utils.ExperienceUtil;
import de.leonhard.storage.Json;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.miketheshadow.complexproficiencies.ComplexProficiencies.levelConfig;

@Command
public class SetLevelCommand extends ComplexCommand {

    public SetLevelCommand() {
        super("setlevel");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, String[] args) {
        if(warnUser(args,sender)) return false;
        Json config = levelConfig;
        int levelXP = config.getInt("levels." + args[1]);
        if(levelXP == 0) {
            try { sender.sendMessage("Level too high!"); }
            catch (Exception e) { Bukkit.getConsoleSender().sendMessage("Level too high!"); }
            return true;
        }
        Player target = (Bukkit.getServer().getPlayer(args[0]));
        CustomUser user = UserDBHandler.getPlayer(target);
        int level = Integer.parseInt(args[1]);
        ExperienceUtil.setPlayerLevel(user,level);
        target.setLevel(level);
        return true;
    }
}
