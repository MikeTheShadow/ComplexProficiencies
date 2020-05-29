package com.miketheshadow.complexproficiencies.command;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ComplexVersionCommand extends ComplexCommand {
    public ComplexVersionCommand() {
        super("cver");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        sender.sendMessage("Currently running ComplexProficiencies Version: " + ComplexProficiencies.VERSION);
        return true;
    }
}
