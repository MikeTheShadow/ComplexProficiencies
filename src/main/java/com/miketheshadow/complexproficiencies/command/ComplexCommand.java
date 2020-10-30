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

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ComplexCommand implements CommandExecutor
{
    private final ComplexProficiencies complexProficiencies;
    public ComplexCommand(String name) {
        //Bukkit.getConsoleSender().sendMessage("Registering command: " + name);
        this.complexProficiencies = ComplexProficiencies.INSTANCE;
        this.complexProficiencies.getCommand(name).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        return false;
    }

    public boolean warnUser(String[] args,CommandSender sender) {
        if(args.length != 2) {
            if(sender instanceof Player) {
                ((Player) sender).getPlayer().sendMessage("Please use correct parameters!");
            }
            else{Bukkit.getConsoleSender().sendMessage("Please Use correct parameters!");}
            return true;
        }
        return false;
    }
}
