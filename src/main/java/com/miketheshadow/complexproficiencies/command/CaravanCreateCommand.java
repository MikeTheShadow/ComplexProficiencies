package com.miketheshadow.complexproficiencies.command;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.caravan.Caravan;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CaravanCreateCommand extends ComplexCommand {
    public CaravanCreateCommand() {
        super("caravancreate");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if(cmd.getName().equalsIgnoreCase("caravancreate")) {
            if(args.length != 3) return false;
            Player player = Bukkit.getPlayer(args[2]);
            if(player == null) return false;
            CustomUser user = UserDBHandler.getPlayer(player);
            int moneyCost = Integer.parseInt(args[0]);
            String zoneName = args[1];
            if(user.getLabor() < Caravan.CRAFTING_COST) {
                player.sendMessage(ChatColor.RED + "You don't have enough labor to do this!");
                return true;
            } else if(!ComplexProficiencies.econ.hasAccount(player)) {
                player.sendMessage(ChatColor.RED + "You don't have enough money!");
                return true;
            } else if(ComplexProficiencies.econ.getBalance(player) < moneyCost) {
                player.sendMessage(ChatColor.RED + "You don't have enough money!");
                return true;
            }
            ComplexProficiencies.econ.withdrawPlayer(player,moneyCost);
            user.setLabor(user.getLabor() - Caravan.CRAFTING_COST);
            user.addExperience("commerce",Caravan.CRAFTING_COST,player);
            UserDBHandler.updatePlayer(user);
            Caravan.createCaravan(player,zoneName,String.valueOf(moneyCost));
            return true;
        }
        return false;
    }
}
