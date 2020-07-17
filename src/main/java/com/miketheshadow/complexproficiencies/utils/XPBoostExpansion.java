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

package com.miketheshadow.complexproficiencies.utils;

import cz.dubcat.xpboost.XPBoostMain;
import cz.dubcat.xpboost.api.XPBoostAPI;
import cz.dubcat.xpboost.constructors.XPBoost;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class XPBoostExpansion extends PlaceholderExpansion {
    private XPBoostMain plugin;

    public XPBoostExpansion() {
    }

    public boolean canRegister() {
        return Bukkit.getPluginManager().getPlugin(this.getPlugin()) != null;
    }

    public boolean register() {
        if (!this.canRegister()) {
            return false;
        } else {
            this.plugin = (XPBoostMain) Bukkit.getPluginManager().getPlugin(this.getPlugin());
            return this.plugin != null && PlaceholderAPI.registerPlaceholderHook(this.getIdentifier(), this);
        }
    }

    public String getAuthor() {
        return "milkwalk";
    }

    public String getIdentifier() {
        return "xpboost";
    }

    public String getPlugin() {
        return "XPBoost";
    }

    public String getVersion() {
        return "1.1.0";
    }

    public String onPlaceholderRequest(Player p, String identifier) {
        if (p == null) {
            return "";
        } else {
            UUID id = p.getUniqueId();
            if (identifier.equals("hasboost")) {
                return XPBoostAPI.hasBoost(id) ? "yes" : "no";
            } else {
                XPBoost xpb = XPBoostAPI.getBoost(id);
                if (xpb == null) {
                    if (identifier.equals("boost_zero")) {
                        return String.valueOf(0);
                    } else if (identifier.equals("timeleft_zero")) {
                        return String.valueOf(0);
                    } else {
                        return identifier.equals("boost_time_zero") ? String.valueOf(0) : "";
                    }
                } else if (identifier.equals("boost_zero")) {
                    return String.valueOf(xpb.getBoost());
                } else if (identifier.equals("timeleft_zero")) {
                    return String.valueOf(xpb.getTimeRemaining());
                } else if (identifier.equals("boost_time_zero")) {
                    return String.valueOf(xpb.getBoostTime());
                } else if (identifier.equals("timeleft")) {
                    return String.valueOf(xpb.getTimeRemaining());
                } else if (identifier.equals("boost")) {
                    return String.valueOf(xpb.getBoost());
                } else {
                    return identifier.equals("boost_time") ? String.valueOf(xpb.getBoostTime()) : null;
                }
            }
        }
    }
}
