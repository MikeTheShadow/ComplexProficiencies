package com.miketheshadow.complexproficiencies.command.experience;

import com.miketheshadow.complexproficiencies.command.ComplexCommand;
import com.miketheshadow.complexproficiencies.Boosters.BoosterNBT;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Optional;

public class CreateBoosterCommand extends ComplexCommand {

    public CreateBoosterCommand() {
        super("CreateBooster");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be executed from console");
            return true;
        }
        Player player = (Player) sender;
        try {
            double boost = Double.parseDouble(args[0]);
            Optional<Integer> time = Optional.of(Integer.parseInt(args[1]));
            NBTItem item = BoosterNBT.createBoosterItem(new NBTItem(BoosterNBT.createBoosterItemStack(boost, time.get())), boost, time.get());
            ItemStack booster = item.getItem();
            Inventory inventory = player.getInventory();
            HashMap<Integer, ItemStack> hashmap = inventory.addItem(booster);
            if (hashmap.isEmpty()) return true;
            player.sendMessage(ChatColor.RED + "Not enough space in inventory.");

        } catch(Exception Ignored) {
            sender.sendMessage(ChatColor.RED + "Illegal Arguments!");
        }
        return false;
    }
}
