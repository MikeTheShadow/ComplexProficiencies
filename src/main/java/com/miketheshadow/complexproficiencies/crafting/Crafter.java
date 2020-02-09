package com.miketheshadow.complexproficiencies.crafting;

import com.miketheshadow.complexproficiencies.crafting.recipe.CustomRecipe;
import com.miketheshadow.complexproficiencies.gui.GenericGUI;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.bukkit.Bukkit;
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
    public boolean crafting = false;
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
            for (ItemStack item: recipe.getRequiredItems())
            {
                if(!inventory.containsAtLeast(item,item.getAmount()))
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
