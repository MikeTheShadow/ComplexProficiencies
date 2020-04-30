package com.miketheshadow.complexproficiencies.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if(event.getEntity().getType() == EntityType.DONKEY) {
            List<ItemStack> drops = event.getDrops();
            drops = new ArrayList<>();
            event.setDroppedExp(0);
        }
    }
}
