package com.miketheshadow.complexproficiencies.utils;

import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class LaborThread implements Runnable
{

    Thread thread;
    String threadName;

    @Override
    public void run() {
        while (true) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Doing labor tick");
            List<CustomUser> players = UserDBHandler.getAllPlayers();
            for (CustomUser user : players) {
                int labor = user.getLabor();
                if(!(labor > 1990)){
                    Player player = Bukkit.getPlayer(user.getName());
                    if(player != null && player.isOnline()) {
                        player.sendMessage(ChatColor.YELLOW + "You gained "
                                + ChatColor.GOLD
                                + "10"
                                + ChatColor.YELLOW
                                + " labor!"
                                + ChatColor.GRAY + " [" + ChatColor.GOLD + (labor + 10) + ChatColor.GRAY + "/" + ChatColor.GOLD + "2000" + ChatColor.GRAY + "]");
                    }
                    user.setLabor(labor + 10);
                    UserDBHandler.updatePlayer(user);
                }
                else if(labor < 2000) {
                    Player player = Bukkit.getPlayer(user.getName());
                    if(player != null && player.isOnline()) {
                        player.sendMessage(ChatColor.YELLOW + "You gained "
                                + ChatColor.GOLD
                                + "10"
                                + ChatColor.YELLOW
                                + " labor! "
                                + ChatColor.GRAY + "[" + ChatColor.GOLD + (2000 - labor) + ChatColor.GRAY + "/" + ChatColor.GOLD + "2000" + ChatColor.GRAY + "]");
                    }
                    user.setLabor(2000);
                    UserDBHandler.updatePlayer(user);
                }
            }
            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void start(String threadName) {

        this.threadName = threadName;
        if (this.thread == null) {
            this.thread = new Thread(this, this.threadName);
            this.thread.start();
        }

    }
}
