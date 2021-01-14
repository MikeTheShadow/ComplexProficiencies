package com.miketheshadow.complexproficiencies.command.experience;

import com.miketheshadow.complexproficiencies.command.ComplexCommand;
import com.miketheshadow.complexproficiencies.Boosters.BoosterUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ClearBoostCommand extends ComplexCommand {

    public ClearBoostCommand() {
        super("ClearBoost");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ClearBoost")) {
            BoosterUtil.setBoost(1, 0);
            sender.sendMessage(ChatColor.GREEN + "The Global Boost has been reset.");
        }
        return true;
    }
}
