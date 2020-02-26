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
