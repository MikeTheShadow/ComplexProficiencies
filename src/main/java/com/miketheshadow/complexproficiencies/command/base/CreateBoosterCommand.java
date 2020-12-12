package com.miketheshadow.complexproficiencies.command.base;

import com.miketheshadow.complexproficiencies.command.ComplexCommand;
import com.miketheshadow.complexproficiencies.utils.NBTManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CreateBoosterCommand extends ComplexCommand {

    public CreateBoosterCommand() {
        super("createbooster");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command cannot be executed from Console!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("createbooster")) {
            if (args.length < 2) {
                return false;
            }
            Player player = (Player) sender;
            ItemStack stack = new ItemStack(Material.EXP_BOTTLE);

            double multiplier;
            int time;

            try {
                multiplier = Double.parseDouble(args[0]);
                time = 1200 * Integer.parseInt(args[1]);
            } catch (Exception ignored) {
                player.sendMessage(ChatColor.RED + "Invalid values.");
                return true;
            }
            ItemStack booster = NBTManager.createBoosterItem(stack, time, multiplier);
            player.getInventory().setItemInMainHand(booster);
            return true;
        }
        return false;
    }
}
