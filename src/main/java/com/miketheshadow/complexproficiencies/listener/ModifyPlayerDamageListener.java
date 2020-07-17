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
