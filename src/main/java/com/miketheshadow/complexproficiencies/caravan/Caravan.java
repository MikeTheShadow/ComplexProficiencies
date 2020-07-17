/*
 *
 *  * Copyright 2019-2020 Michael Pape and contributors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.miketheshadow.complexproficiencies.caravan;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Caravan {

    public static final int CRAFTING_COST = 100;
    public static final int RETURN_COST = 150;


    public static void createCaravan(Player player, String zoneName,String value) {
        //TODO make this create a special crafting effect
        generateDonkey(player,zoneName,value);
    }
    public static void generateDonkey(Player player, String zoneName,String value) {
        World world = player.getWorld();
        //create the special donkey
        Donkey donkey = (Donkey) world.spawnEntity(player.getLocation(), EntityType.DONKEY);
        donkey.setTamed(true);
        donkey.setAdult();
        donkey.setAI(false);
        donkey.setCarryingChest(true);
        donkey.setCollidable(true);
        //donkey.setGlowing(true);
        donkey.setInvulnerable(true);
        donkey.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        donkey.addPassenger(player);
        donkey.setCustomName(player.getName() + "'s Caravan");
        donkey.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);
        //donkey.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(3);
        donkey.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(0);
        donkey.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2);
        donkey.setMaximumAir(0);
        donkey.setInvulnerable(true);
        player.getOpenInventory().close();

        //create special item
        ItemStack item = new ItemStack(Material.DIRT);
        item.setItemMeta(Bukkit.getItemFactory().getItemMeta(Material.DIRT));
        ItemMeta meta = item.getItemMeta();
        List<String> lore =  new ArrayList<>();
        Location location = player.getLocation();
        //set player name
        lore.add(player.getName()); // 0
        //add co-ords
        lore.add(String.valueOf(location.getBlockX()));// 1
        lore.add(String.valueOf(location.getBlockZ()));// 2
        //owner is riding
        lore.add("true");// 3
        lore.add(zoneName);// 4
        lore.add(value); // 5
        meta.setLore(lore);
        meta.setDisplayName(player.getName());
        item.setItemMeta(meta);
        donkey.getInventory().setItem(0,item);
        //schedule donkey's death
        Bukkit.getScheduler().scheduleSyncDelayedTask(ComplexProficiencies.INSTANCE, () -> {
            if(player.isOnline() && !donkey.isDead())player.sendMessage("Your caravan expired!");
            donkey.remove();
        }, 72000L);
    }
}
