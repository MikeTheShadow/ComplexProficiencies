package com.miketheshadow.complexproficiencies.Boosters;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class BoosterTask extends BukkitRunnable {

    private final double boost;

    public BoosterTask(ComplexProficiencies complexProficiencies, double boost) {
        this.boost = boost;
    }

    @Override
    public void run() {
        BoosterUtil.decreaseBoost(boost);
        Bukkit.broadcastMessage(ChatColor.WHITE + "" + boost + "x Boost " + ChatColor.GREEN + "has ended.");
    }
}
