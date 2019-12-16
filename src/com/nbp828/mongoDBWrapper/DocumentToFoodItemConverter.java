package com.nbp828.mongoDBWrapper;

import com.nbp828.Common.FoodItem;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DocumentToFoodItemConverter {

    public static FoodItem Convert(Document document){
        Set<String> keys = document.keySet();
        String code = null;
        String name = null;
        Integer score = null;
        List<String> categories = null;
        List<String> ingredients = null;

        // code
        if (keys.contains("code")){
           code = document.getString("code");
        }

        // name
        if (keys.contains("product_name")){
            name = document.getString("product_name");
        }
        if (keys.contains("brands")) {
            name = document.getString("brands") + " " + name;
        }

        // score
        if (keys.contains("nutriscore_score")){
            score = document.getInteger("nutriscore_score");
        }

        // categories
        if (keys.contains("categories_hierarchy")){
            categories = (ArrayList<String>)document.get("categories_hierarchy");
        }

        // ingredients
        if (keys.contains("ingredients_hierarchy")){
            ingredients = (ArrayList<String>)document.get("ingredients_hierarchy");
        }

        // check of 0
        if (categories.size() == 0){
            return null;
        }

        if (ingredients.size() == 0){
            return null;
        }

        // TODO: Clean ingredients

        return new FoodItem(code, name, score, categories, ingredients);
    }
}
