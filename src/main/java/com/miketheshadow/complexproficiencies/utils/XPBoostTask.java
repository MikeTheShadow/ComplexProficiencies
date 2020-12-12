package com.miketheshadow.complexproficiencies.utils;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class XPBoostTask extends BukkitRunnable {

    private final ComplexProficiencies complexProficiencies;
    private final double multiplier;

    public XPBoostTask(ComplexProficiencies complexProficiencies, double multiplier) {
        this.complexProficiencies = complexProficiencies;
        this.multiplier = multiplier;
    }

    @Override
    public void run() {
        double currentMultiplier = ComplexProficiencies.xpMultiplier;
        ComplexProficiencies.xpMultiplier = (currentMultiplier / multiplier);
        Bukkit.broadcastMessage(ChatColor.WHITE + "" + multiplier + "x Boost "
                + ChatColor.GREEN + "has ended.");
    }
}
