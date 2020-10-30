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

package com.miketheshadow.complexproficiencies.gui.recipe;

import com.miketheshadow.complexproficiencies.gui.RecipeDatabase;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private ObjectId id;

    @BsonProperty(value = "primary_tag")
    private String tag;

    @BsonProperty(value = "sub_tags")
    private List<String> subTags = new ArrayList<>();

    @BsonProperty(value = "item_name")
    private String itemName;

    private List<String> ingredients = new ArrayList<>();

    @BsonProperty(value = "item_data")
    private String itemNBT;

    @BsonProperty(value = "labor_cost")
    private int laborCost;

    @BsonProperty(value = "money_cost")
    private int moneyCost;

    public Recipe() {

    }

    public Recipe(ItemStack stack, String tag) {
        this.tag = tag;
        if(!stack.hasItemMeta() || !stack.getItemMeta().hasDisplayName()) {
            throw new IllegalArgumentException("ItemStack must have a display name. ItemStack#hasDisplayName()");
        }
        this.itemName = stack.getItemMeta().getDisplayName();
        NBTContainer container = NBTItem.convertItemtoNBT(stack);
        this.itemNBT = container.toString();
    }

    public void save() {
        if(RecipeDatabase.getRecipe(this.getItemName()) == null) {
            RecipeDatabase.createRecipe(this);
        } else
            RecipeDatabase.updateRecipe(this);
    }

    public void setId(ObjectId id) { this.id = id; }

    public ObjectId getId() { return id; }

    public void addSubTag(String tag) {this.subTags.add(tag);}

    public void addItem(String item) {this.ingredients.add(item);}

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<String> getSubTags() {
        return subTags;
    }

    public void setSubTags(List<String> subTags) {
        this.subTags = subTags;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getItemNBT() {
        return itemNBT;
    }

    public void setItemNBT(String itemNBT) {
        this.itemNBT = itemNBT;
    }

    public int getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(int laborCost) {
        this.laborCost = laborCost;
    }

    public int getMoneyCost() {
        return moneyCost;
    }

    public void setMoneyCost(int moneyCost) {
        this.moneyCost = moneyCost;
    }
}
