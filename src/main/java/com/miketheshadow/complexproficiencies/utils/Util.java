package com.miketheshadow.complexproficiencies.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util
{
    public enum CustomInventoryType
    {
        WEAPONSMITHING,ARMORSMTHING
    }
    public static HashMap<String,Integer> fixMap(ResultSet set) throws SQLException {
        Gson gson = new Gson();
        String prof = set.getString("professions");
        Type type = new TypeToken<HashMap<String, Integer>>(){}.getType();
        //original map
        HashMap<String, String> map = gson.fromJson(prof, type);
        //convereted map
        HashMap<String,Integer> returnMap = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            int value = Integer.parseInt(entry.getValue());
            returnMap.put(key,value);
        }
        return returnMap;
    }

}
