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

package com.miketheshadow.complexproficiencies.gui.recipe;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventorySetupListener implements Listener {

    @EventHandler
    public void closeEvent(InventoryCloseEvent event) {

        HumanEntity player = event.getPlayer();
        if(event.getView().getTitle().equals("Item Editor")) {
            if(!ComplexRecipeCommand.editingList.containsKey(player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "Unable to find you in editor database. Please re-edit the item.");
                return;
            }
            Recipe recipe = ComplexRecipeCommand.editingList.get(player.getUniqueId());
            List<String> items = new ArrayList<>();
            for(ItemStack i : event.getInventory().getContents()) {
                if(i != null) {
                    items.add(NBTItem.convertItemtoNBT(i).toString());
                }
            }
            recipe.setIngredients(items);
            recipe.save();
            player.sendMessage(ChatColor.GREEN + "Item updated successfully!");
        }
        ComplexRecipeCommand.editingList.remove(player.getUniqueId());
    }

}
