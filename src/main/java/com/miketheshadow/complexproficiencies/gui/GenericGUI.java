package com.miketheshadow.complexproficiencies.gui;

import com.miketheshadow.complexproficiencies.crafting.Category;
import com.miketheshadow.complexproficiencies.crafting.CustomRecipe;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.CategoryDBHandler;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.RecipeDBHandler;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GenericGUI {

    public static void addCategory(Player player, String guiName) {
        Inventory inventory = Bukkit.createInventory(player, 54, guiName);
        inventory.setItem(45, TagRegister.previousPage);
        inventory.setItem(49, TagRegister.registerBuilder(guiName,"subcategory"));
        inventory.setItem(53, TagRegister.nextPage);
        player.openInventory(addCategories(inventory, guiName));
    }
    public static void addRecipe(Player player, String guiName, int levelReq, int exp,String prof) {
        NBTContainer nbtItem = NBTItem.convertItemtoNBT(TagRegister.registerBuilder(guiName,"subrecipe"));
        String location = nbtItem.getCompound("tag").getCompound("display").getString("path");
        location += "/" + guiName;
        Inventory inventory = Bukkit.createInventory(player, 54, guiName);
        Inventory checkInventory = addCategories(inventory, location);
        nbtItem.getCompound("tag").getCompound("display").setInteger("levelReq",levelReq);
        nbtItem.getCompound("tag").getCompound("display").setInteger("exp",exp);
        nbtItem.getCompound("tag").getCompound("display").setString("prof",prof);
        ItemStack item = NBTItem.convertNBTtoItem(nbtItem);
        checkInventory.setItem(45, TagRegister.previousPage);
        checkInventory.setItem(49,item);
        checkInventory.setItem(53, TagRegister.nextPage);
        player.openInventory(addCategories(checkInventory, guiName));
    }
    public static void baseGUI(Player player, String guiName) {
        Inventory inventory = Bukkit.createInventory(player, 54, guiName);
        inventory.setItem(45, TagRegister.previousPage);
        inventory.setItem(49, TagRegister.registerCrafting(Material.RED_SHULKER_BOX.toString(),"PAGE 1",guiName.toLowerCase()));
        inventory.setItem(53, TagRegister.nextPage);
        player.openInventory(addCategories(inventory, guiName));
    }
    public static void nextWindowGUI(Player player, String guiName,ItemStack item) {
        NBTContainer nbtItem = NBTItem.convertItemtoNBT(item);
        String location = nbtItem.getCompound("tag").getCompound("display").getString("path");
        location += "/" + guiName;
        Inventory inventory = Bukkit.createInventory(player, 54, guiName);
        Inventory checkInventory = addCategories(inventory, location);
        if(checkInventory.getContents().length < 4) { return; }
        nbtItem.getCompound("tag").getCompound("display").setString("path",location.toLowerCase());
        item = NBTItem.convertNBTtoItem(nbtItem);
        checkInventory.setItem(45, TagRegister.previousPage);
        checkInventory.setItem(49,item);
        checkInventory.setItem(53, TagRegister.nextPage);
        player.openInventory(checkInventory);
    }
    public static void BuilderGUI(Player player, String guiName, ItemStack item, String type) {
        NBTContainer nbtItem = NBTItem.convertItemtoNBT(item);
        nbtItem.getCompound("tag").getCompound("display").setString("type",type);
        item = NBTItem.convertNBTtoItem(nbtItem);
        Inventory inventory = Bukkit.createInventory(player, 54, guiName);
        inventory.setItem(45, TagRegister.previousPage);
        inventory.setItem(49,item);
        inventory.setItem(53, TagRegister.nextPage);
        player.openInventory(addCategories(inventory, guiName));
    }
    public static void craftingGUI(Player player, String guiName, ItemStack item, String type,String name) {
        NBTContainer nbtItem = NBTItem.convertItemtoNBT(item);
        nbtItem.getCompound("tag").getCompound("display").setString("type",type);
        nbtItem.getCompound("tag").getCompound("display").setString("Name",ChatColor.BLUE + "CRAFT");
        item = NBTItem.convertNBTtoItem(nbtItem);
        Inventory inventory = Bukkit.createInventory(player, 54, guiName);
        inventory.setItem(45, TagRegister.previousPage);
        inventory.setItem(49,item);
        inventory.setItem(53, TagRegister.nextPage);
        CustomRecipe recipe = RecipeDBHandler.getRecipeByItem(name);
        inventory.addItem(NBTItem.convertNBTtoItem(recipe.getItemToBeCrafted()));
        for (ItemStack i: recipe.getRequiredItems()) { inventory.addItem(i); }
        player.openInventory(inventory);
    }
    public static Inventory addCategories(Inventory inventory,String path) {
        path = path.toLowerCase();
        if(path.charAt(0) != '/') path = "/" + path;
        List<Category> categories = CategoryDBHandler.getSubCategories(path);
        for (Category category: categories) {
            try {
                NBTContainer container = new NBTContainer(category.getIcon());
                container.getCompound("tag").getCompound("display").setString("type","category");
                inventory.addItem(NBTItem.convertNBTtoItem(container));
            }catch (Exception ignored){}
        }
        return addRecipes(inventory,path);
    }
    public static Inventory addRecipes(Inventory inventory,String path) {
        path = path.toLowerCase();
        if(path.charAt(0) != '/') path = "/" + path;
        List<CustomRecipe> recipes = RecipeDBHandler.getRecipesByParent(path);
        for (CustomRecipe recipe: recipes) {
            try {
                NBTCompound item = recipe.getItemToBeCrafted();
                item.getCompound("tag").getCompound("display").setString("type","recipe");
                inventory.addItem(NBTItem.convertNBTtoItem(item));
            }catch (Exception ignored){}
        }
        return inventory;
    }
}
