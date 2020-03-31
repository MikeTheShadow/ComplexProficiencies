package com.miketheshadow.complexproficiencies.listener;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class ModifyPlayerDamageListener implements Listener
{
    @EventHandler(priority =  EventPriority.LOWEST)
    public void playerDamageEvent(EntityDamageEvent event)
    {
        if(damageList().contains(event.getCause()))
        {
            if(event.getEntity() instanceof LivingEntity)
            {
                LivingEntity entity = (LivingEntity)event.getEntity();
                double maxHP = entity.getMaxHealth();
                event.setDamage(maxHP * .01);
            }
        }
    }
    public static List<EntityDamageEvent.DamageCause> damageList()
    {
        List<EntityDamageEvent.DamageCause> list = new ArrayList<>();
        list.add(EntityDamageEvent.DamageCause.FIRE_TICK);
        list.add(EntityDamageEvent.DamageCause.POISON);
        list.add(EntityDamageEvent.DamageCause.WITHER);
        return list;
    }

}
