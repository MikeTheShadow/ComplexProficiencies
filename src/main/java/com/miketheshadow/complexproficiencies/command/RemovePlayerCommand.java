package com.miketheshadow.complexproficiencies.command;

import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RemovePlayerCommand extends ComplexCommand {

    public RemovePlayerCommand() {
        super("complexremoveplayer");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        if(args.length != 1) return false;
        Player player = Bukkit.getPlayer(args[0]);
        if(player == null) {
            sender.sendMessage("Player does not exist!");
            return true;
        } else {
            UserDBHandler.removePlayer(UserDBHandler.getPlayer(player));
        }
        return true;
    }

}
