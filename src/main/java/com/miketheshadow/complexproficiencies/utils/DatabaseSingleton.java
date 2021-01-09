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

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.bukkit.configuration.file.FileConfiguration;

public class DatabaseSingleton {

    private static final ConnectionString DB_STRING = init();

    private static ConnectionString init() {
        if(DB_STRING != null) return DB_STRING;
        FileConfiguration config = ComplexProficiencies.authenticationConfig;
        String username = config.getString("username");
        String password = config.getString("password");
        String serverAddress = config.getString("server_address");
        int serverPort = config.getInt("server_port");
        return new ConnectionString("mongodb://" + username + ":" + password +"@" + serverAddress +":" + serverPort +"/?authSource=admin");
    }

    public static ConnectionString getMongoConnection() {
        return DB_STRING;
    }

}
