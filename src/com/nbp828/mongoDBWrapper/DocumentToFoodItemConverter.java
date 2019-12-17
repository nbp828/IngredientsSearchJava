package com.nbp828.mongoDBWrapper;

import com.nbp828.Common.FoodItem;
import com.nbp828.Common.IngredientsCleaner;
import org.bson.Document;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Set;

public class DocumentToFoodItemConverter {

    public static FoodItem Convert(Document document) {
        try
        {
            Set<String> keys = document.keySet();
            String code = null;
            String name = null;
            Integer score = null;
            ArrayList<String> categories = null;
            ArrayList<String> ingredients = null;

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

            // Do not create non-english
            for (String ingredient : ingredients) {
                if (!isPureAscii(ingredient)) {
                    return null;
                }
            }

            IngredientsCleaner ingredientsCleaner = new IngredientsCleaner();
            ingredients = ingredientsCleaner.getCleanIngredients(ingredients);

            // check of 0
            if (categories.size() == 0){
                return null;
            }

            if (ingredients.size() == 0){
                return null;
            }

            // TODO: Clean categories

            return new FoodItem(code, name, score, categories, ingredients);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            System.out.println(document.toString());
        }

        return null;
    }

    public static boolean isPureAscii(String v) {
        return Charset.forName("US-ASCII").newEncoder().canEncode(v);
    }
}
