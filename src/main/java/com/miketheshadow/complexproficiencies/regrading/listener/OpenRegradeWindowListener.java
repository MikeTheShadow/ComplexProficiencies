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

package com.miketheshadow.complexproficiencies.regrading.listener;

import com.miketheshadow.complexproficiencies.regrading.Regrading;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OpenRegradeWindowListener implements Listener {

    @EventHandler
    public void rightClickEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if(!itemStack.hasItemMeta())return;
        if(!itemStack.getItemMeta().hasLore())return;
        if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        String title = itemStack.getItemMeta().getDisplayName();
        if(title.contains(Regrading.WEAPON_REGRADE_SCROLL) || title.contains(Regrading.ARMOR_REGRADE_SCROLL)) {
            openRegradeInventory(player,Regrading.INVENTORY_TITLE,itemStack,title);
        }
    }

    public void openRegradeInventory(Player player,String title,ItemStack regradeScroll,String itemName) {
        ItemStack stack;
        if(itemName.contains(Regrading.WEAPON_REGRADE_SCROLL)) {
            stack = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName("Weapon");
            stack.setItemMeta(meta);
        } else {
            stack = new ItemStack(Material.DIAMOND_CHESTPLATE);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName("Armor");
            stack.setItemMeta(meta);
        }
        Inventory inventory = Bukkit.createInventory(player,54,title);
        inventory.setItem(24,regradeScroll);
        inventory.getItem(24).setAmount(1);
        inventory.setItem(20,stack);
        player.openInventory(inventory);
    }
}
