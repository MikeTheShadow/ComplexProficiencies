package com.miketheshadow.complexproficiencies.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.crafting.recipe.CustomRecipe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeDBHandler
{
    public static String DBName = "Recipes.sqlite";
    public static void createDatabase()
    {
        File db = new File(ComplexProficiencies.getPlugin(ComplexProficiencies.class).getDataFolder() + "\\" + DBName);
        if(db.exists())
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "DB exists ignoring...");
            return;
        }
        String url = "jdbc:sqlite:" + ComplexProficiencies.getPlugin(ComplexProficiencies.class).getDataFolder() + "\\" + DBName;

        try (Connection conn = DriverManager.getConnection(url))
        {
            if (conn != null)
            {
                DatabaseMetaData meta = conn.getMetaData();
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Recipe Database creation successful!");
            }
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
        createRecipeTable();
    }

    public static Connection connect()
    {
        Connection conn = null;
        try
        {
            // db parameters
            String url = "jdbc:sqlite:" + ComplexProficiencies.getPlugin(ComplexProficiencies.class).getDataFolder() + "\\" + DBName;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            return conn;
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
        return null;
    }

    public static void createRecipeTable()
    {
        // SQLite connection string
        String url = "jdbc:sqlite:" + ComplexProficiencies.getPlugin(ComplexProficiencies.class).getDataFolder() + "\\" + DBName;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Recipes (\n"
                + "    item text NOT NULL,\n"
                + "    ingredients text NOT NULL,\n"
                + "    parent text NOT NULL,\n"
                + "    level integer NOT NULL,\n"
                + "    experience integer NOT NULL"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement())
        {
            // create a new table
            stmt.execute(sql);
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
    }

    public static void insertNewRecipe(CustomRecipe recipe)
    {
        String sql = "INSERT INTO Recipes(item,ingredients,parent,level,experience) VALUES(?,?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            Gson gson = new Gson();
            String profs = gson.toJson(recipe.getRequiredItems());
            pstmt.setString(1, recipe.getItemToBeCrafted().toString());
            pstmt.setString(2,profs);
            pstmt.setString(3, recipe.getParent());
            pstmt.setInt(4, recipe.getLevelReq());
            pstmt.setInt(5, recipe.getXpGain());
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage("Error! " + e.getSQLState());
        }
    }

    public static List<CustomRecipe> getRecipesByParent(String parent)
    {
        String sql = "SELECT item,ingredients,parent,level,experience "
                + "FROM Recipes WHERE parent = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql))
        {
            // set the value
            pstmt.setString(1,parent);
            //
            ResultSet rs  = pstmt.executeQuery();
            // loop through the result set
            List<CustomRecipe> recipes = new ArrayList<>();
            while (rs.next())
            {
                Gson gson = new Gson();
                List<String> items = gson.fromJson(rs.getString("ingredients"),new TypeToken<List<String>>(){}.getType());
                recipes.add(new CustomRecipe(items,rs.getString("item"),rs.getString("parent"),rs.getInt("level"),rs.getInt("experience")));
            }
            return recipes;
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage("Error! " + e.getMessage());
        }
        return null;
    }
}
