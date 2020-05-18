package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.ExperienceUtil;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import de.leonhard.storage.Json;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.List;
import static com.miketheshadow.complexproficiencies.ComplexProficiencies.levelConfig;

public class ExperienceCommandListener implements CommandExecutor {

    private final ComplexProficiencies complexProficiencies;

    public ExperienceCommandListener(ComplexProficiencies complexProficiencies) {
        this.complexProficiencies = complexProficiencies;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("userstats"))
        {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be run by a player.");
                return true;
            }
            if(args.length > 1)return false;
            CustomUser user;
            Player player = (Player) sender;
            if(args.length == 0) {
                user = UserDBHandler.getPlayer(player);
            }
            else {
                Player otherPlayer = Bukkit.getServer().getPlayer(args[0]);
                user = UserDBHandler.getPlayer(otherPlayer);
            }
            if(user.getPurses() == null) {
                sender.sendMessage("User " + args[0] + " does not exist!");
                return true;
            }
            StringBuilder builder = new StringBuilder();
            //open stats
            builder.append("\n").append(StringUtils.repeat("§5~",20)).append("\n");
            //add username
            builder.append(StringUtils.repeat(" ",10 - (user.getName().length()/2)));
            builder.append("§6").append(user.getName()).append("\n").append("\n \n");

            int level = user.getLevelXP()[0];

            //experience information if user is max level do not display information
            if(levelConfig.getInt("levels." + (level + 1))  != 0 ) {
                builder.append("§3Level: ").append(level).append("\n \n");
                builder.append("§2Current Experience: §a").append(ExperienceUtil.getPlayerCurrentXP(user)).append("§e/§a").append(levelConfig.getInt("levels." + level)).append("\n \n");
            }
            else {
                builder.append("§3Level: ").append(level).append("§5 (MAX)").append("\n \n");
            }
            //finally add total Experience
            builder.append("§9Total Experience: §c").append(user.getLevelXP()[1]).append("\n");
            //close stats
            builder.append(StringUtils.repeat("§5~",20));
            player.sendMessage(builder.toString());
            return true;
        }
        else if(cmd.getName().equalsIgnoreCase("mystats")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be run by a player.");
            }
            if(args.length > 0)return false;
            Player player = (Player) sender;
            CustomUser user = UserDBHandler.getPlayer(player);
            StringBuilder builder = new StringBuilder();
            //open stats
            builder.append("\n").append(StringUtils.repeat("§5~",20)).append("\n");
            //add username
            builder.append(StringUtils.repeat(" ",10 - (user.getName().length()/2)));
            builder.append("§6").append(user.getName()).append("\n").append("\n \n");

            int level = user.getLevelXP()[0];

            //experience information if user is max level do not display information
            if(levelConfig.getInt("levels." + (level + 1))  != 0 ) {
                builder.append("§3Level: ").append(level).append("\n \n");
                builder.append("§2Current Experience: §a").append(ExperienceUtil.getPlayerCurrentXP(user)).append("§e/§a").append(levelConfig.getInt("levels." + level)).append("\n \n");
            }
            else {
                builder.append("§3Level: ").append(level).append("§5 (MAX)").append("\n \n");
            }
            //finally add total Experience
            builder.append("§9Total Experience: §c").append(user.getLevelXP()[1]).append("\n");
            //close stats
            builder.append(StringUtils.repeat("§5~",20));
            player.sendMessage(builder.toString());
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("fixexperience")) {
            List<CustomUser> customUsers = UserDBHandler.getAllPlayers();
            levelConfig.forceReload();
            ComplexProficiencies.rebuildLevelMap();
            for (CustomUser user: customUsers)
            {
                ExperienceUtil.addPlayerExperience(user,Bukkit.getPlayer(user.getName()),0,true,false);
            }
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("setexperience")) {
            if(warnUser(args,sender)) return false;
            Player target = (Bukkit.getServer().getPlayer(args[0]));
            if (target != null)
            {
                CustomUser user = UserDBHandler.getPlayer(target);
                int[] levelXP = user.getLevelXP();
                levelXP[1] = Integer.parseInt(args[1]);
                user.setLevelXP(levelXP);
                ExperienceUtil.addPlayerExperience(user,target,0,true,false);
                return true;
            }
            else return false;
        }
        else if (cmd.getName().equalsIgnoreCase("setlevel")) {
            if(warnUser(args,sender)) return false;
            Json config = levelConfig;
            int levelXP = config.getInt("levels." + args[1]);
            if(levelXP == 0) {
                try { sender.sendMessage("Level too high!"); }
                catch (Exception e) { Bukkit.getConsoleSender().sendMessage("Level too high!"); }
                return true;
            }
            Player target = (Bukkit.getServer().getPlayer(args[0]));
            CustomUser user = UserDBHandler.getPlayer(target);
            int level = Integer.parseInt(args[1]);
            ExperienceUtil.setPlayerLevel(user,level);
            target.setLevel(level);
            return true;
        }
        //ADD EXPERIENCE
        else if(cmd.getName().equalsIgnoreCase("addexperience")) {
            if(warnUser(args,sender)) return false;

            Player target = (Bukkit.getServer().getPlayer(args[0]));
            if(target == null) {
                sender.sendMessage("Player does not exist!");
                return true;
            }
            CustomUser user = UserDBHandler.getPlayer(target);
            try {
                int xpToAdd = Integer.parseInt(args[1]);
                ExperienceUtil.addPlayerExperience(user,target,xpToAdd,false,false);
            }
            catch (Exception e) {
                Bukkit.getServer().getConsoleSender().sendMessage("Error adding experience check that values are correct!");
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }
    public boolean warnUser(String[] args,CommandSender sender) {
        if(args.length != 2) {
            if(sender instanceof Player) {
                ((Player) sender).getPlayer().sendMessage("Please use correct parameters!");
            }
            else{Bukkit.getConsoleSender().sendMessage("Please Use correct parameters!");}
            return true;
        }
        return false;
    }

}
