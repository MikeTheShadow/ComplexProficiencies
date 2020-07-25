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

import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.ExperienceUtil;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class PlayerXPListener implements Listener {


    @EventHandler
    public void playerGainsXPEvent(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        CustomUser user = UserDBHandler.getPlayer(player);
        //update user information
        //ExperienceUtil.addPartyExperience(user,player,event.getAmount(),false,true);
        ExperienceUtil.addPlayerExperience(user,player,event.getAmount(),false,true);
        event.setAmount(0);
        player.setExp(0);
    }
}
