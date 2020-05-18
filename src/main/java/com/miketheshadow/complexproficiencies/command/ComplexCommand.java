package com.miketheshadow.complexproficiencies.command;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

class ComplexCommand implements CommandExecutor
{
    private ComplexProficiencies complexProficiencies;
    public ComplexCommand(String name) {
        this.complexProficiencies = ComplexProficiencies.INSTANCE;
        this.complexProficiencies.getCommand(name).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        return false;
    }

}
