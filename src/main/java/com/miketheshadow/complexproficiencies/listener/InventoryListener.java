package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.crafting.Category;
import com.miketheshadow.complexproficiencies.crafting.CustomRecipe;
import com.miketheshadow.complexproficiencies.gui.TagRegister;
import com.miketheshadow.complexproficiencies.gui.GenericGUI;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.CategoryDBHandler;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.RecipeDBHandler;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.omg.CORBA.TRANSACTION_MODE;

import java.util.ArrayList;
import java.util.List;

public class InventoryListener implements Listener {

    @EventHandler
    public void inventorySlotClickedEvent(InventoryClickEvent event) {

        //Check that the user is using my inventory
        Player player = (Player)event.getWhoClicked();
        Inventory topInventory = player.getOpenInventory().getTopInventory();
        //Now we know we're in the custom inventory. Check if they clicked an item
        ItemStack itemClicked = event.getCurrentItem();
        if(!event.getClick().isLeftClick() || itemClicked == null || itemClicked.getType() == Material.AIR)return;
        boolean isNotAButton = isNotAButton(itemClicked,topInventory);
        //Locate the current directory the user is in using the hidden NBT tags on the page located in position 49
        if(topInventory.contains(TagRegister.nextPage))event.setCancelled(true);
        else return;

        NBTContainer clickedNBT = NBTItem.convertItemtoNBT(itemClicked);
        String clickedType = getType(clickedNBT);

        NBTContainer nbtContainer = NBTItem.convertItemtoNBT(topInventory.getItem(49));
        String type = getType(nbtContainer);

        if(isNotAButton && getType(NBTItem.convertItemtoNBT(topInventory.getItem(49))).equals("subBuilder")) { event.setCancelled(false); }
        if(isNotAButton && getType(NBTItem.convertItemtoNBT(topInventory.getItem(49))).equals("recipeBuilder")) { event.setCancelled(false); }
        if(clickedType.equals("subcategory")) {
            GenericGUI.BuilderGUI(player,"CATEGORY BUILDER",itemClicked,"subBuilder");
        } else if(clickedType.equals("subrecipe")) {
            GenericGUI.BuilderGUI(player,"RECIPE BUILDER",itemClicked,"recipeBuilder");
        } else if(clickedType.equals("crafting")) {
            PlayerInventory playerInventory = player.getInventory();
            CustomUser user = UserDBHandler.getPlayer(player);
            CustomRecipe recipe = RecipeDBHandler.getRecipeByItem(topInventory.getItem(0).getItemMeta().getDisplayName());
            if(user.getProfessions().get(recipe.getProfession()) < recipe.getLevelReq()) {
                player.sendMessage(ChatColor.RED + "Your require level " + recipe.getLevelReq() + " in " + recipe.getProfession());
                return;
            }
            for (ItemStack item: recipe.getRequiredItems())
            {
                if(!playerInventory.containsAtLeast(item,item.getAmount()))
                {
                    player.sendMessage(ChatColor.RED + "You don't have enough materials to craft this!");
                    return;
                }
            }
            for (ItemStack item: recipe.getRequiredItems()) playerInventory.removeItem(item);
            playerInventory.addItem(NBTItem.convertNBTtoItem(recipe.getItemToBeCrafted()));
        } else if(clickedType.equals("subBuilder")) {
            for (ItemStack item: topInventory.getContents())
            {
                if(item == null);
                else if(isNotAButton(item,topInventory) && item.getType() != Material.AIR)
                {
                    NBTContainer currentItem = NBTItem.convertItemtoNBT(item);
                    String title = currentItem.getCompound("tag").getCompound("display").getString("Name");
                    String icon = currentItem.toString();
                    String path = nbtContainer.getCompound("tag").getCompound("display").getString("path");
                    Category category = new Category(title,icon,path);
                    if(!CategoryDBHandler.checkCategory(category))
                    {
                        player.sendMessage(ChatColor.RED + "Error! Item appears to already be in the database!");
                        player.sendMessage("DEBUG PATH: " + path);
                    }
                    else { player.sendMessage(ChatColor.GREEN + "Adding new item " + title); }

                }
            }
            player.getOpenInventory().close();
        } else if(clickedType.equals("recipeBuilder")) {
            ItemStack buildItem = topInventory.getContents()[0];
            List<ItemStack> recipeList = new ArrayList<>();
            if(buildItem == null) {
                player.sendMessage(ChatColor.RED + "You need to add an item to the recipe!");
                return;
            }
            for (ItemStack item: topInventory.getContents()) {
                if(item == null) continue;
                if(buildItem == item) continue;
                if(isNotAButton(item,topInventory) && item.getType() != Material.AIR) {
                    recipeList.add(item);
                }
            }
            if(recipeList.size() < 1) {
                player.sendMessage(ChatColor.RED + "You need to add ingredients to the recipe!");
                return;
            }
            recipeList.remove(0);
            int levelReq = clickedNBT.getCompound("tag").getCompound("display").getInteger("levelReq");
            int exp = clickedNBT.getCompound("tag").getCompound("display").getInteger("exp");
            String prof = clickedNBT.getCompound("tag").getCompound("display").getString("prof");
            String path = clickedNBT.getCompound("tag").getCompound("display").getString("path");
            String name = NBTItem.convertItemtoNBT(buildItem).getCompound("tag").getCompound("display").getString("Name");
            CustomRecipe recipe = new CustomRecipe(recipeList,NBTItem.convertItemtoNBT(buildItem),levelReq,exp,prof,path,name);
            RecipeDBHandler.checkRecipe(recipe);
            player.sendMessage(ChatColor.GREEN + "Added new recipe: " + ChatColor.GOLD + name);
        } else if(clickedType.equals("category")) {
            String title = NBTItem.convertItemtoNBT(itemClicked).getCompound("tag").getCompound("display").getString("Name");
            GenericGUI.nextWindowGUI(player,title,topInventory.getItem(49));
        } else if(clickedType.equals("recipe")) {
            String title = NBTItem.convertItemtoNBT(itemClicked).getCompound("tag").getCompound("display").getString("Name");
            GenericGUI.craftingGUI(player,title,topInventory.getItem(49),"crafting",title);
        }


    }
    private String getType(NBTContainer nbtItem) {
        try {
            return nbtItem.getCompound("tag").getCompound("display").getString("type");
        }catch (Exception e){
            return "";
        }

    }

    @EventHandler
    public void openInventory(InventoryOpenEvent event) {
        if(event.getPlayer().isInsideVehicle() && event.getPlayer().getVehicle().getType() == EntityType.DONKEY) event.setCancelled(true);
        try {
            if(event.getInventory().getItem(0).getItemMeta().getLore().get(3).equals("true"))event.setCancelled(true);
            else if(event.getInventory().getItem(0).getItemMeta().getLore().get(3).equals("false"))event.setCancelled(true);
        }catch (Exception ignored){}

    }

    private static boolean isNotAButton(ItemStack itemClicked, Inventory topInventory) {
        try {
            if(itemClicked.getItemMeta() == null) return true;
            String item = itemClicked.getItemMeta().getDisplayName();
            String item1 = topInventory.getItem(45).getItemMeta().getDisplayName();
            String item2 = topInventory.getItem(49).getItemMeta().getDisplayName();
            String item3 = topInventory.getItem(53).getItemMeta().getDisplayName();
            if(item.equals(item3) || item.equals(item2) || item.equals(item1)) return false;
        }catch (Exception ignored){}
        return true;
    }


}
