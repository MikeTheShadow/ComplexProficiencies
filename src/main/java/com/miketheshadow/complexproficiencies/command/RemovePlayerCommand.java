package com.miketheshadow.complexproficiencies.command;

import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RemovePlayerCommand extends ComplexCommand {

    public RemovePlayerCommand() {
        super("cremoveplayer");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        if(args.length != 1) return false;
        OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
        long code = UserDBHandler.removePlayer(UserDBHandler.getPlayer(player));
        sender.sendMessage(ChatColor.GREEN + "Removed player '" + player.getName() + "' level data with code: " + code);
        return true;
    }

}
