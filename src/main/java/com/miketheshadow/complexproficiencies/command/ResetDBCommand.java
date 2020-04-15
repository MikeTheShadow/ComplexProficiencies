package com.miketheshadow.complexproficiencies.command;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResetDBCommand extends ComplexCommand
{

    private static boolean active = false;

    public ResetDBCommand() {
        super("resetdb");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if(cmd.getName().equalsIgnoreCase("resetdb")) {
            if(!active && ComplexProficiencies.levelConfig.getBoolean("reset")) {
                active = true;
                Bukkit.broadcastMessage(ChatColor.RED + "DELETING ALL USER DATA IN COMPLEX PROFICIENCIES in 30s RUN THIS COMMAND AGAIN TO CANCEL!");
                Bukkit.getScheduler().scheduleSyncDelayedTask(ComplexProficiencies.INSTANCE, () -> {
                    if(active) {
                        Bukkit.broadcastMessage(ChatColor.RED + "REMOVING PLAYER DATA...");
                        List<CustomUser> userList = UserDBHandler.getAllPlayers();
                        for (CustomUser user: userList) {
                            UserDBHandler.removePlayer(user);
                        }
                        Bukkit.broadcastMessage(ChatColor.RED + "COMPLETE!");
                    }
                }, 600);
            } else if(active) {
                Bukkit.broadcastMessage(ChatColor.RED + "DEACTIVATING DATA REMOVAL!");
                active = false;
            } else {
                sender.sendMessage(ChatColor.RED + "Reset is disabled!");
            }
            return true;
        }
        return false;
    }
}
