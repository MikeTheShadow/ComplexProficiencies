package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.crafting.Category;
import com.miketheshadow.complexproficiencies.gui.BaseCategories;
import com.miketheshadow.complexproficiencies.gui.GenericGUI;
import com.miketheshadow.complexproficiencies.utils.CategoryDBHandler;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        if(topInventory.contains(BaseCategories.nextPage))event.setCancelled(true);
        else return;

        NBTContainer clickedNBT = NBTItem.convertItemtoNBT(itemClicked);
        String clickedType = getType(clickedNBT);

        NBTContainer nbtContainer = NBTItem.convertItemtoNBT(topInventory.getItem(49));
        String type = getType(clickedNBT);

        if(isNotAButton && getType(NBTItem.convertItemtoNBT(topInventory.getItem(49))).equals("subBuilder")) { event.setCancelled(false); }
        if(clickedType.equals("subcategory")) {
            GenericGUI.subCategoryBuilderGUI(player,"BUILDER",itemClicked);
        } else if(type.equals("subBuilder")) {
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
                    else { Bukkit.broadcastMessage(ChatColor.GREEN + "Adding new item " + title); }

                }
            }
            player.getOpenInventory().close();
        } else if(topInventory.contains(itemClicked) && isNotAButton) {
            String title = NBTItem.convertItemtoNBT(itemClicked).getCompound("tag").getCompound("display").getString("Name");
            GenericGUI.nextWindowGUI(player,title,topInventory.getItem(49));
        } else if(isNotAButton) {

            String location = NBTItem.convertItemtoNBT(topInventory.getItem(49)).getCompound("tag").getCompound("display").getString("path");
            List<Category> categoryList =  CategoryDBHandler.getSubCategories(location);

            if(!categoryList.isEmpty() && !categoryList.get(0).getIcon().equals("")) {
                for(int i = 0; i < categoryList.size(); i++) {
                    ItemStack stack = NBTItem.convertNBTtoItem(new NBTContainer(categoryList.get(i).getIcon()));
                    topInventory.addItem(stack);
                }
            }

        }


    }
    private String getType(NBTContainer nbtItem) {
        try {
            return nbtItem.getCompound("tag").getCompound("display").getString("type");
        }catch (Exception e){
            return "";
        }

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
