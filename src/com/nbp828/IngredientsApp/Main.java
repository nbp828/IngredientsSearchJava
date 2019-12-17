package com.nbp828.IngredientsApp;

import com.nbp828.Common.FoodItem;
import com.nbp828.IngredientsScorer.ScoringAlgorithm;
import com.nbp828.LuceneWrapper.LuceneDataDirectoryBuilder;
import com.nbp828.LuceneWrapper.LuceneIndexer;
import com.nbp828.LuceneWrapper.LuceneResult;
import com.nbp828.LuceneWrapper.LuceneSearcher;
import com.nbp828.mongoDBWrapper.IngredientsMongoClient;
import com.nbp828.mongoDBWrapper.QueryBuilder;
import org.bson.conversions.Bson;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // createLuceneInputDirectory();
        //printFoodItem("3493832070384");
        //printFoodItem("0000000043595");
        //printFoodItem("0000010206515");
        //createLuceneIndexDirectory();

        // Score Queries
        IngredientsMongoClient mongoClient = new IngredientsMongoClient();


        // Dave's healthy query
        searchLuceneAndScore(mongoClient, "CRACKED WHOLE WHEAT water, POWERSEED MIX, wheat-gluten, gluten, " +
                "fruit-juice, fruit, OAT FIBER SEA SALT, CULTURED WHOLE WHEAT, yeast, vinegar, whole-wheat, cereal, " +
                "wheat");

        // Sig Kit unhealthy query
        searchLuceneAndScore(mongoClient,"wheat-flour, cereal, wheat, flour, cereal-flour, water, " +
                "wheat-gluten, glucose-fructose-syrup, soya-bean, soya");

    }

    private static void searchLuceneAndScore(IngredientsMongoClient mongoClient, String query){

        String indexPath = "LuceneIndex";
        LuceneSearcher searcher = new LuceneSearcher();
        ScoringAlgorithm scoringAlgorithm = new ScoringAlgorithm(mongoClient);
        try{
            ArrayList<LuceneResult> results = searcher.Search(indexPath, query);
            float score = scoringAlgorithm.score(results);
            System.out.println(score);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
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
