package com.miketheshadow.complexproficiencies.listener;


import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import me.realized.duels.api.arena.Arena;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.awt.*;

import static com.miketheshadow.complexproficiencies.ComplexProficiencies.levelConfig;

public class PlayerAttacksListener implements Listener
{
    @EventHandler(priority =  EventPriority.LOWEST)
    public void onEntityAttacksEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) return;
        int levelDif = levelConfig.getInt("settings.levelDifference");
        //WORLDGUARD BS
        Location loc = event.getEntity().getLocation();
        WorldGuardPlugin worldGuardPlugin =  WorldGuardPlugin.getPlugin(WorldGuardPlugin.class);
        RegionContainer worldguard = WorldGuardPlugin.getPlugin(WorldGuardPlugin.class).getRegionContainer();
        RegionQuery query = worldguard.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(loc);
        try { if(!set.allows(DefaultFlag.PVP,worldGuardPlugin.wrapPlayer((Player) event.getEntity()))) { return; } }
        catch (Exception e) { e.printStackTrace(); }
        //END OF WORLDGUARD BS
        Player att = (Player) event.getDamager();
        Player def = (Player) event.getEntity();
        CustomUser attacker = UserDBHandler.getPlayer(att);
        CustomUser defender = UserDBHandler.getPlayer(def);

        //make sure they're not dueling. If they are we don't care
        if(playersInDuel(att,def)) return;
        if(Math.abs(attacker.getLevelXP()[0] - defender.getLevelXP()[0]) >= levelDif) {
            att.sendMessage(levelConfig.getString("settings.attackMessage"));
            event.setDamage(0);
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void entityDamageEvent(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Entity vehicle = player.getVehicle();
            if(vehicle != null && !event.isCancelled() && event.getDamage() > 0) {
                vehicle.removePassenger(player);
            }
        }
        if(event.getEntity().getType() == EntityType.DONKEY) event.setCancelled(true);
    }

    public boolean playersInDuel(Player attacker, Player defender)
    {
        Arena arena = ComplexProficiencies.duelsApi.getArenaManager().get(attacker);
        if(arena == null) return false;
        return arena.has(attacker) && arena.has(defender);
    }

}
