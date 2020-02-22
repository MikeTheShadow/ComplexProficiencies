package com.miketheshadow.complexproficiencies.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDBHandler
{
    public static String DBName = "Users.sqlite";
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
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "User Database creation successful!");
            }
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
        createUserTable();
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

    public static void createUserTable()
    {
        // SQLite connection string
        String url = "jdbc:sqlite:" + ComplexProficiencies.getPlugin(ComplexProficiencies.class).getDataFolder() + "\\" + DBName;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Users (\n"
                + "    name text NOT NULL,\n"
                + "    UID text NOT NULL,\n"
                + "    professions text NOT NULL"
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
    public static void insertNewUser(String name, String UID)
    {
        String sql = "INSERT INTO Users(name,UID,professions) VALUES(?,?,?)";
        CustomUser user = new CustomUser(name,UID);
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            Gson gson = new Gson();
            String profs = gson.toJson(user.getProfessions());
            pstmt.setString(1, name);
            pstmt.setString(2, UID);
            pstmt.setString(3, profs);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage("Error! " + e.getSQLState());
        }
    }
    public static CustomUser getUserByID(String UUID)
    {
        String sql = "SELECT name,UID,professions "
                + "FROM Users WHERE UID = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql))
        {

            // set the value
            pstmt.setString(1,UUID);
            //
            ResultSet rs  = pstmt.executeQuery();
            // loop through the result set
            while (rs.next())
            {
                return new CustomUser(rs.getString("name"),rs.getString("UID"),  Util.fixMap(rs));
            }
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage("Error! " + e.getMessage());
        }
        return null;
    }
    public static void updateCustomUser(CustomUser user)
    {
        String sql = "UPDATE Users SET professions = ? "
                + "WHERE UID = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            Gson gson = new Gson();
            String profs = gson.toJson(user.getProfessions());
            // set the corresponding param
            pstmt.setString(1, profs);
            pstmt.setString(2, user.getUid());
            // update
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage("Error! " + e.getMessage());
        }
    }
    public static List<CustomUser> getAllUsers()
    {
        String sql = "SELECT name,UID,level,professions "
                + "FROM Users";

        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql))
        {
            ResultSet rs  = pstmt.executeQuery();
            // loop through the result set
            List<CustomUser> returnList = new ArrayList<>();
            while (rs.next())
            {
                returnList.add(new CustomUser(rs.getString("name"),rs.getString("UID"),  Util.fixMap(rs)));
            }
            return returnList;
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage("Error! " + e.getMessage());
        }
        return null;
    }
}
