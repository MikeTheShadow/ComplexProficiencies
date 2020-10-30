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

import com.miketheshadow.complexproficiencies.command.ComplexCommand;
import com.miketheshadow.complexproficiencies.gui.RecipeDatabase;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class ComplexRecipeCommand extends ComplexCommand {

    public static HashMap<UUID,Recipe> editingList = new HashMap<>();

    public ComplexRecipeCommand() {
        super("crecipe");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You cannot run this command as console!");
            return true;
        }
        Player player = (Player) sender;
        ItemStack stack = player.getItemInHand();
        //display help data and check to make sure item in hand is a valid item for recipes
        if(infoCommands(player,stack,args)) return true;
        else if(createDestroyCommands(player,stack,args)) return true;
        else if(addSetDataCommands(player,stack,args)) return true;
        else if(editInventoryCommand(player,stack,args)) return true;
        else if(viewItemCommand(player,stack,args)) return true;
        return true;
    }

    public boolean viewItemCommand(Player player, ItemStack stack,String[] args) {
        if(args[0].equals("view")) {
            Recipe recipe = RecipeDatabase.getRecipe(stack.getItemMeta().getDisplayName());
            if(recipe == null) {
                player.sendMessage(ChatColor.RED + "Unable to get recipe for item: " + stack.getItemMeta().getDisplayName());
                return true;
            }
            String rInfo = quickFormat("Name: ",recipe.getItemName())
                    + quickFormat("\nPrimary Tag: ",recipe.getTag())
                    + quickFormat("\nSecondary Tag(s): ", Arrays.toString(recipe.getSubTags().toArray()))
                    + quickFormat("\nLabor Cost: ", "" + recipe.getLaborCost())
                    + quickFormat("\nMoney Cost: ","" + recipe.getMoneyCost());
            player.sendMessage(rInfo);
        }
        return false;
    }

    public String quickFormat(String p1,String p2) {
        return ChatColor.YELLOW + p1 + "" + ChatColor.GRAY + p2;
    }

    public boolean editInventoryCommand(Player player, ItemStack stack,String[] args) {
        if(args[0].equals("edit")) {
            Recipe recipe = RecipeDatabase.getRecipe(stack.getItemMeta().getDisplayName());
            if(recipe == null) {
                player.sendMessage(ChatColor.RED + "Unable to get recipe for item: " + stack.getItemMeta().getDisplayName());
                return true;
            }
            editingList.put(player.getUniqueId(),recipe);
            Inventory inventory = Bukkit.createInventory(player,54,"Item Editor");
            for(String item : recipe.getIngredients()) {
                inventory.addItem(NBTItem.convertNBTtoItem(new NBTContainer(item)));
            }
            player.openInventory(inventory);
            return true;
        }
        return false;
    }


    public boolean addSetDataCommands(Player player, ItemStack stack, String[] args) {
        if(args[0].equals("set")) {
            if(args.length != 3) {
                player.sendMessage(ChatColor.YELLOW + "/crecipe set [stat] [value]");
                return true;
            }
            try {
                int stat = Integer.parseInt(args[2]);
                Recipe recipe = RecipeDatabase.getRecipe(stack.getItemMeta().getDisplayName());
                if(recipe == null) {
                    player.sendMessage(ChatColor.RED + "Unable to get recipe for item: " + stack.getItemMeta().getDisplayName());
                    return true;
                }
                if(args[1].equals("laborcost")) {
                    recipe.setLaborCost(stat);
                } else if(args[1].equals("moneycost")) {
                    recipe.setMoneyCost(stat);
                } else {
                    player.sendMessage(ChatColor.RED + "Unknown tag " + args[1]);
                    return true;
                }
                recipe.save();
                player.sendMessage(ChatColor.GREEN + "Updated " + args[1] + " to " + stat);
            } catch (Exception ignored) {
                player.sendMessage(ChatColor.RED + "Unkown integer " + args[2]);
            }
            return true;
        }
        else if(args[0].equals("add")) {
            if(args.length != 3) {
                player.sendMessage(ChatColor.YELLOW + "/crecipe add [stat] [value]");
                return true;
            }
            Recipe recipe = RecipeDatabase.getRecipe(stack.getItemMeta().getDisplayName());
            if(recipe == null) {
                player.sendMessage(ChatColor.RED + "Unable to get recipe for item: " + stack.getItemMeta().getDisplayName());
                return true;
            }
            if(args[1].equals("subtag")) {
                recipe.addSubTag(args[2]);
                recipe.save();
                player.sendMessage(ChatColor.GREEN + "Added subtag: " + args[2]);
            }
            else {
                player.sendMessage(ChatColor.RED + "unknown tag: " + args[1]);
            }
            return true;
        }
        return false;
    }

    public boolean createDestroyCommands(Player player, ItemStack stack, String[] args) {
        if(args[0].equals("create")) {
            if(args.length < 2) {
                player.sendMessage(ChatColor.YELLOW + "/crecipe create [primarytag] (laborcost) (moneycost)");
                return true;
            }
            Recipe recipe = RecipeDatabase.getRecipe(stack.getItemMeta().getDisplayName());
            if(recipe != null) {
                player.sendMessage(ChatColor.RED + "Recipe already exists! To recreate it run /crecipe destroy");
                return true;
            }
            recipe = new Recipe(stack,args[1]);
            if(args.length == 4) {
                try {
                    int lc = Integer.parseInt(args[2]);
                    int mc = Integer.parseInt(args[3]);
                    recipe.setLaborCost(lc);
                    recipe.setMoneyCost(mc);
                } catch (Exception ignored) {
                    player.sendMessage(ChatColor.RED + "Invalid number " + args[3] + " or " + args[4]);
                }
            }
            recipe.save();
            player.sendMessage(ChatColor.GREEN + "Recipe added to database successfully!");
            return true;
        }
        if(args[0].equals("destroy")) {
            Recipe recipe = RecipeDatabase.getRecipe(stack.getItemMeta().getDisplayName());
            if(recipe == null) {
                player.sendMessage(ChatColor.RED + "Unable to find item: "+ stack.getItemMeta().getDisplayName());
            }
            else {
                RecipeDatabase.removeRecipe(recipe);
                player.sendMessage(ChatColor.GREEN + "Recipe removed from database!");
            }
            return true;
        }
        return false;
    }

    public boolean infoCommands(Player player, ItemStack stack, String[] args) {
        if(args.length == 0) {
            player.sendMessage(ChatColor.YELLOW + "Run '/crecipe help' for the help menu");
            return true;
        } else if(args[0].equals("help")) {
            String msg = ChatColor.YELLOW + "\n/crecipe create [primarytag] (laborcost) (moneycost)"
                    + ChatColor.GRAY +      "\nCreates a recipe for the item in your hand"
                    + ChatColor.YELLOW +    "\n/crecipe delete"
                    + ChatColor.GRAY +      "\nDeletes the recipe of the item in your hand"
                    + ChatColor.YELLOW +    "\n/crecipe set [stat] [amount]"
                    + ChatColor.GRAY +      "\nSets the stat for the recipe for the item in your hand.\nFor a list of stats use /crecipe stats"
                    + ChatColor.GRAY +      "\nex: /crecipe set laborcost 5"
                    + ChatColor.YELLOW +    "\n/crecipe add [addstat] [value]"
                    + ChatColor.GRAY +      "\nAdds a stat to the recipe of the item in your hand"
                    + ChatColor.GRAY +      "\nFor a list of addable stats use: /crecipe addstats"
                    + ChatColor.YELLOW +    "\n/crecipe edit"
                    + ChatColor.GRAY +      "\nOpens the recipe editor. Close the editor to save the changes"
                    + ChatColor.YELLOW +    "\n/crecipe view"
                    + ChatColor.GRAY +      "\nView the final result";
            player.sendMessage(msg);
            return true;
        } else if(args[0].equals("stats")) {
            player.sendMessage(getStats());
            return true;
        } else if(args[0].equals("addstats")) {
            player.sendMessage(getAddStats());
            return true;
        } else if(stack == null || !stack.hasItemMeta() || !stack.getItemMeta().hasDisplayName()) {
            player.sendMessage(ChatColor.RED + "Invalid item in hand! Item is missing a custom name!");
            return true;
        }
        return false;
    }

    public String getStats() {
        return ChatColor.YELLOW + "[laborcost,moneycost]";
    }
    public String getAddStats() {
        return ChatColor.YELLOW + "[subtag]";
    }
}
