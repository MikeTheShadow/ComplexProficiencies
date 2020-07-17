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
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RegradeInventoryListener extends Regrading implements Listener {

    @EventHandler
    public void onInventoryClickedEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        //check if player is in the regrading inventory
        if(!event.getView().getTitle().equals(INVENTORY_TITLE)) return;
        event.setCancelled(true);
        if(player.getInventory() != event.getClickedInventory() || event.getCurrentItem() == null)return;
        if(event.getClick() != ClickType.RIGHT)return;
        ItemStack stack = event.getCurrentItem();
        Inventory topInventory = player.getOpenInventory().getTopInventory();
        if(isRegradable(stack)) {
            if(isWeapon(stack) || isArmor(stack)) {
                if(topInventory.getItem(24) == null) {
                    topInventory.setItem(20,stack);
                    return;
                }
            }
            if(isWeapon(stack)) {
                if(topInventory.getItem(24).getItemMeta().getDisplayName().contains(WEAPON_REGRADE_SCROLL)) {
                    topInventory.setItem(20,stack);
                    regradeItem(player,stack,topInventory.getItem(24));
                } else {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"");
                    player.sendMessage(ChatColor.RED + "You need to use a Weapon Regrade Scroll!");
                }
            } else if(isArmor(stack)) {
                if(topInventory.getItem(24).getItemMeta().getDisplayName().contains(ARMOR_REGRADE_SCROLL)) {
                    topInventory.setItem(20,stack);
                } else {
                    player.sendMessage(ChatColor.RED + "You need to use an Armor Regrade Scroll!");
                }
            }
        }
    }





}
