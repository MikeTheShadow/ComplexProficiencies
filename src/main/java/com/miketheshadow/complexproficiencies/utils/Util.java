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

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Util {
    public static HashMap<String, Integer> fixMap(ResultSet set) throws SQLException {
        Gson gson = new Gson();
        String profs = set.getString("professions");
        Type type = new TypeToken<HashMap<String, Integer>>() {
        }.getType();
        //original map
        HashMap<String, Integer> map = gson.fromJson(profs, type);
        return map;
    }

    public enum CustomInventoryType {
        WEAPONSMITHING, ARMORSMTHING
    }

}
