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

public class RightClickListener implements Listener {

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
        inventory.setItem(20,stack);
        player.openInventory(inventory);
    }
}
