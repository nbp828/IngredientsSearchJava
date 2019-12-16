package com.nbp828.ingredients;

import com.nbp828.Common.FoodItem;
import com.nbp828.LuceneWrapper.LuceneDataDirectoryBuilder;
import com.nbp828.mongoDBWrapper.IngredientsMongoClient;
import com.nbp828.mongoDBWrapper.QueryBuilder;
import org.bson.conversions.Bson;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // createLuceneInputDirectory();
        //printFoodItem("3493832070384");
        //printFoodItem("0000000043595");
        //printFoodItem("0000010206515");
        abc();

    }

    private static void abc(){

    }

    private static void printFoodItem(String code){
        IngredientsMongoClient mongoClient = new IngredientsMongoClient();
        Bson query = QueryBuilder.getOneFoodItemQuery(code);
        ArrayList<FoodItem> items = mongoClient.getFoodItems(query, true);

        for (FoodItem item : items)
        {
            System.out.println(item.toString());
        }
    }

    private static void createLuceneInputDirectory() {
        IngredientsMongoClient mongoClient = new IngredientsMongoClient();
        LuceneDataDirectoryBuilder builder = new LuceneDataDirectoryBuilder(mongoClient);
        try
        {
            builder.buildDirectory("LuceneInput");
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
}
