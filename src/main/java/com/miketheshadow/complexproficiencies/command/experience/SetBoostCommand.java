package com.miketheshadow.complexproficiencies.command.experience;

import com.miketheshadow.complexproficiencies.command.ComplexCommand;
import com.miketheshadow.complexproficiencies.Boosters.BoosterUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SetBoostCommand extends ComplexCommand {

    public SetBoostCommand() {
        super("SetBoostCommand");
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("SetBoost")) {
            double boost;
            Optional<Integer> time;
            try {
                boost = Double.parseDouble(args[0]);
                time = Optional.of(Integer.parseInt(args[1]));
                if (boost < 0 || boost > 1.5) {
                    sender.sendMessage(ChatColor.RED + "The boost must be between 0 or 1.5");
                }
                BoosterUtil.setBoost(boost, time.get());
            } catch(Exception Ignored) {
                sender.sendMessage("Illegal Arguments!");
            }
        }
        return true;
    }
}
