package com.miketheshadow.complexproficiencies.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class LaborThread implements Runnable {

    Thread thread;
    @Override
    public void run() {
        while(true){
            List<CustomUser> players = UserDBHandler.getAllPlayers();
            for (CustomUser user : players)
            {
                int labor = user.getLabor();
                if(!(labor > 1995)){
                    Player player = Bukkit.getPlayer(user.getName());
                    if(player != null && player.isOnline())player.sendMessage(ChatColor.YELLOW + "You gained 5 labor!");
                    user.setLabor(labor + 5);
                    UserDBHandler.updatePlayer(user);
                }
            }
            try { Thread.sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace(); }
        }
    }
    public void start()
    {
        if(thread == null)
        {
            thread = new Thread(this,"laborThread");
            thread.start();
        }
    }
}
