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

package com.miketheshadow.complexproficiencies.gui;

import com.miketheshadow.complexproficiencies.api.DatabaseAPI;
import com.miketheshadow.complexproficiencies.gui.recipe.Recipe;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class RecipeDatabase {

    public static final MongoCollection<Recipe> collection = init();

    public static void createRecipe(Recipe recipe) { collection.insertOne(recipe); }

    /**
     *
     * @param itemName the itemName in string format.
     * @return the recipe in the database. If one doesn't exist return null.
     */
    public static Recipe getRecipe(String itemName) {
        return collection.find(eq("item_name",itemName)).first();
    }

    public static void removeRecipe(Recipe recipe) {
        collection.deleteOne(new Document("_id", recipe.getId())).getDeletedCount();
    }

    /**
     *
     * @param recipe the recipe to be updated
     */
    public static void updateRecipe(Recipe recipe) {
        FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
        collection.findOneAndReplace(new Document("_id", recipe.getId()), recipe, returnDocAfterReplace);
    }

    public static MongoCollection<Recipe> init() {
        if(collection == null) {
            ConnectionString connectionString = DatabaseAPI.getDatabaseConnection();
            CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder()
                    .register(Recipe.class)
                    .automatic(true)
                    .build());
            CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
            MongoClientSettings clientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .codecRegistry(codecRegistry)
                    .build();
            MongoClient mongoClient = MongoClients.create(clientSettings);
            MongoDatabase db = mongoClient.getDatabase("ComplexProficiencies");
            return db.getCollection("Recipe", Recipe.class);
        }
        return collection;
    }


}
