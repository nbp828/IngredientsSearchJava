package com.nbp828.ingredients;

import com.nbp828.Common.FoodItem;
import com.nbp828.LuceneWrapper.LuceneDataDirectoryBuilder;
import com.nbp828.LuceneWrapper.LuceneIndexer;
import com.nbp828.LuceneWrapper.LuceneSearcher;
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
        //createLuceneIndexDirectory();

        // Dave's healthy query
        searchLucene("CRACKED WHOLE WHEAT water, POWERSEED MIX, wheat-gluten, gluten, fruit-juice, fruit," +
                " OAT FIBER SEA SALT, CULTURED WHOLE WHEAT, yeast, vinegar, whole-wheat, cereal, wheat");

        // Sig Kit unhealthy query
        searchLucene("wheat-flour, cereal, wheat, flour, cereal-flour, water, wheat-gluten," +
                " gluten, high-fructose-corn-syrup, glucose, fructose, corn-syrup," +
                " glucose-fructose-syrup, soya-bean, soya");

    }

    private static void searchLucene(String query){
        String indexPath = "LuceneIndex";
        LuceneSearcher searcher = new LuceneSearcher();
        searcher.Search(indexPath, query);
    }

    private static void createLuceneIndexDirectory(){
        String indexPath = "LuceneIndex";
        String docPath = "LuceneInput";
        LuceneIndexer indexer = new LuceneIndexer();
        indexer.createIndex(indexPath, docPath);
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
