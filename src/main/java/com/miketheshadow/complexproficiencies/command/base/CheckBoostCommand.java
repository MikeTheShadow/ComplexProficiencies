package com.miketheshadow.complexproficiencies.command.base;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.command.ComplexCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CheckBoostCommand extends ComplexCommand {

    public CheckBoostCommand() {
        super("checkboost");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("checkboost")) {
            double multiplier = ComplexProficiencies.xpMultiplier;
            sender.sendMessage(ChatColor.GREEN + "Current Boost: " + ChatColor.WHITE +  multiplier + "x");
            return true;
        }
        return false;
    }
}
