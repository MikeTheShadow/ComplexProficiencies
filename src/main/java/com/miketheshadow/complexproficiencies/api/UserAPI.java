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
import com.mongodb.client.MongoDatabase;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class UserAPI {

    @Deprecated
    public static CustomUser getUser(Player player){
        return UserDBHandler.getPlayer(player);
    }

    public static boolean userHasLabor(Player player,int amount) {
        return UserDBHandler.getPlayer(player).getLabor() >= amount;
    }

    public static void updateUserProf(Player player, String prof, int amount) {
        prof = prof.toLowerCase();
        UserDBHandler.getPlayer(player).addExperience(prof,amount,player);
    }

    public static void updateUser(CustomUser user){
        UserDBHandler.updatePlayer(user);
    }

    public static int getProfLevel(Player player,String profname) {
        profname = profname.toLowerCase();
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
        profname = profname.toLowerCase();
        CustomUser user = UserDBHandler.getPlayer(player);
        user.addExperience(profname,experienceAmount,player);
        UserDBHandler.updatePlayer(user);
    }

    public static int getUserLabor(Player player) {
        return UserDBHandler.getPlayer(player).getLabor();
    }
}
