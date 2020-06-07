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
    public static int regenAmount = 30;
    public static int cap = 2000;
    @Override
    public void run() {
        while (true) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Doing labor tick");
            List<CustomUser> players = UserDBHandler.getAllPlayers();
            for (CustomUser user : players) {
                int labor = user.getLabor();
                if(!(labor > cap - regenAmount)){
                    Player player = Bukkit.getPlayer(user.getName());
                    if(player != null && player.isOnline()) {
                        player.sendMessage(ChatColor.YELLOW + "You gained "
                                + ChatColor.GOLD
                                + regenAmount
                                + ChatColor.YELLOW
                                + " labor!"
                                + ChatColor.GRAY + " [" + ChatColor.GOLD + (labor + regenAmount) + ChatColor.GRAY + "/" + ChatColor.GOLD + cap + ChatColor.GRAY + "]");
                    }
                    user.setLabor(labor + regenAmount);
                    UserDBHandler.updatePlayer(user);
                }
                else if(labor < cap) {
                    Player player = Bukkit.getPlayer(user.getName());
                    if(player != null && player.isOnline()) {
                        player.sendMessage(ChatColor.YELLOW + "You gained "
                                + ChatColor.GOLD
                                + regenAmount
                                + ChatColor.YELLOW
                                + " labor! "
                                + ChatColor.GRAY + "[" + ChatColor.GOLD + cap + ChatColor.GRAY + "/" + ChatColor.GOLD + cap + ChatColor.GRAY + "]");
                    }
                    user.setLabor(cap);
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
