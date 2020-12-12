package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.utils.NBTManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    ComplexProficiencies complexProficiencies = ComplexProficiencies.INSTANCE;

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.hasItem()) {
            NBTManager.checkForBooster(event.getItem(), complexProficiencies, event);
        }
    }
}
