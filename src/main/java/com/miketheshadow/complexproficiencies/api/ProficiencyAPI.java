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

package com.miketheshadow.complexproficiencies.api;

import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.entity.Player;
import java.util.HashMap;

@Deprecated
public class ProficiencyAPI {

    public static int getProfLevel(Player player,String profname) {
        CustomUser user = UserDBHandler.getPlayer(player);
        if(user.getProfessions() == null) return -1;
        return user.getLevelFromProf(profname);
    }

    public static HashMap<String, Integer> getAllProfs(Player player) {
        CustomUser user = UserDBHandler.getPlayer(player);
        if(user.getProfessions() == null) return null;
        return user.getProfessions();
    }

    public static void addExperienceToProf(Player player,String profname,int experienceAmount) {
        CustomUser user = UserDBHandler.getPlayer(player);
        user.addExperience(profname,experienceAmount,player);
        UserDBHandler.updatePlayer(user);
    }
}
