package com.miketheshadow.complexproficiencies.crafting;

import com.miketheshadow.complexproficiencies.crafting.recipe.CustomRecipe;
import com.miketheshadow.complexproficiencies.gui.GenericGUI;
import com.miketheshadow.complexproficiencies.utils.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Crafter
{
    public ItemStack itemToCraft = null;
    public ItemStack itemType = null;
    public CustomRecipe recipe = null;
    public List<CustomRecipe> recipes = null;
    public Player player;
    public boolean transfer = false;
    public GenericGUI currentGUI;
    public Crafter(Player player, GenericGUI currentGUI)
    {
        this.player = player;
        this.currentGUI = currentGUI;
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
