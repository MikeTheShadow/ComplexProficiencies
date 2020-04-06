package com.miketheshadow.complexproficiencies.caravan;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Caravan {

    public static void createEntity(Player player) {
        World world = player.getWorld();
        Donkey donkey = (Donkey) world.spawnEntity(player.getLocation(), EntityType.DONKEY);
        donkey.setTamed(true);
        donkey.setAdult();
        donkey.setAI(false);
        donkey.setCarryingChest(true);
        donkey.setCollidable(true);
        donkey.setGlowing(true);
        donkey.setInvulnerable(true);
        donkey.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        donkey.addPassenger(player);
        donkey.setCustomName(player.getName() + "'s Caravan");
        donkey.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);
        donkey.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(0);
        donkey.setMaximumAir(0);
        donkey.setMaxHealth(2);
        donkey.setInvulnerable(true);
        player.getOpenInventory().close();
    }
}
