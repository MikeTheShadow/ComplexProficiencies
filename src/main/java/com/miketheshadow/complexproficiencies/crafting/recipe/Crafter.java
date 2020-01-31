package com.miketheshadow.complexproficiencies.crafting.recipe;

import com.miketheshadow.complexproficiencies.utils.Util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class Crafter
{
    public ItemStack itemToCraft = null;
    public ItemStack itemType = null;
    public Util.CustomInventoryType inventoryType = null;
    public CustomRecipe recipe = null;
    public Player player;
    public boolean transfer = false;
    public Crafter(Player player)
    {
        this.player = player;
    }
    public boolean canCraft()
    {
        if(itemToCraft != null && itemType != null && recipe != null)
        {
            Inventory inventory = player.getInventory();
            for (CustomItem item: recipe.requiredItems)
            {
                if(!inventory.contains(Material.valueOf(item.typeName),item.amount))
                {
                    player.sendMessage("You don't have enough materials!");
                    return false;
                }
            }
            player.sendMessage("Crafted!");
            return true;
        }
        return false;
    }

}
