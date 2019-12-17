package com.nbp828.IngredientsApp;

import com.nbp828.Common.FoodItem;
import com.nbp828.Common.IngredientsCleaner;
import com.nbp828.IngredientsScorer.ScoringAlgorithm;
import com.nbp828.LuceneWrapper.LuceneDataDirectoryBuilder;
import com.nbp828.LuceneWrapper.LuceneIndexer;
import com.nbp828.LuceneWrapper.LuceneResult;
import com.nbp828.LuceneWrapper.LuceneSearcher;
import com.nbp828.mongoDBWrapper.IngredientsMongoClient;
import com.nbp828.mongoDBWrapper.QueryBuilder;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        // createLuceneInputDirectory();
        //printFoodItem("3493832070384");
        //printFoodItem("0000000043595");
        //printFoodItem("0000010206515");
        //createLuceneIndexDirectory();

        // Score Queries
        IngredientsMongoClient mongoClient = new IngredientsMongoClient();

        // runAnalysis(mongoClient);
        searchTest(mongoClient);

    }

    private static void searchTest(IngredientsMongoClient mongoClient)
    {
        String s1 = "en:ingredient, en:CRACKED WHOLE WHEAT, en:water, en:POWERSEED MIX, en:wheat-gluten, en:gluten, " +
                "en:fruit-juice, en:fruit, en:OAT FIBER SEA SALT, en:CULTURED WHOLE WHEAT, en:yeast, en:vinegar, " +
                "en:whole-wheat, en:cereal, en:wheat";
        String s2 = "en:wheat-flour, en:cereal, en:wheat, en:flour, en:cereal-flour, en:water, en:wheat-gluten, " +
                "en:gluten, en:high-fructose-corn-syrup, en:glucose, en:fructose, en:corn-syrup, " +
                "en:glucose-fructose-syrup, en:soya-bean, en:soya";
        String s3 = "en:WHOLE WHEAT FLOUR, en:filtered-water, en:water, en:sugar, en:salt, en:yeast, en:e282";
        String s4 = "en:Unbromated unbleached enriched _wheat_ flour, en:water, en:oat, en:cereal, " +
                "en:high-fructose-corn-syrup, en:glucose, en:fructose, en:corn-syrup, en:glucose-fructose-syrup, " +
                "en:yeast, en:soya-oil, en:oil, en:vegetable-oil-and-fat, en:vegetable-oil, en:contains 2% and less of";
        String s5 = "en:Sprouted Wheat, en:Sprouted Barley, en:Sprouted Millet, en:malted-barley, en:cereal, en:malt," +
                " en:Sprouted Lentils, en:Sprouted Soybeans, en:Sprouted Spelt, en:filtered-water, en:water, " +
                "en:fresh-yeast, en:yeast, en:baker-s-yeast, en:wheat-gluten";


        searchLuceneAndScore(mongoClient, s1);
        searchLuceneAndScore(mongoClient, s2);
        searchLuceneAndScore(mongoClient, s3);
        searchLuceneAndScore(mongoClient, s4);
        searchLuceneAndScore(mongoClient, s5);

        String m1 = "Organic avocado puree (water, organic avocado), nonorganic avocado oil, organic cane sugar, " +
                "organic tapioca starch, organic cocoa powder, organic vanilla extract, sea salt, " +
                "organic guar gum, organic gun acacia";
        String m2 = "avocado puree,  avocado oil,  cane sugar, " +
                " tapioca starch,  cocoa powder,  vanilla extract, sea salt, " +
                " guar gum,  gun acacia";

        String m3 = "Milk, sugar, cream, chocolate, dextrose, milk fat, cocoa butter, carob gum, vanilla extract, " +
                "natural flavor, chocolate (processed with alkali), soy lecithin, mint leaf extractives.";

        String m4 = "Sugar, cocoa butter, milk, chocolate, skim milk, soya lecithin (emulsifier), " +
                "barley malt powder, artificial flavor";

        String m5 = "potato";

        String m6 = "avocado";

        String m7 = "sugar";

        searchLuceneAndScore(mongoClient, m1);
        searchLuceneAndScore(mongoClient, m2);
        searchLuceneAndScore(mongoClient, m3);
        searchLuceneAndScore(mongoClient, m4);
        searchLuceneAndScore(mongoClient, m5);
        searchLuceneAndScore(mongoClient, m6);
        searchLuceneAndScore(mongoClient, m7);

//        // Dave's healthy query
//        searchLuceneAndScore(mongoClient, "CRACKED WHOLE WHEAT water, POWERSEED MIX, wheat-gluten, gluten, " +
//                "fruit-juice, fruit, OAT FIBER SEA SALT, CULTURED WHOLE WHEAT, yeast, vinegar, whole-wheat, cereal, " +
//                "wheat");
//
//        // Sig Kit unhealthy query
//        searchLuceneAndScore(mongoClient,"wheat-flour, cereal, wheat, flour, cereal-flour, water, " +
//                "wheat-gluten, glucose-fructose-syrup, soya-bean, soya");
    }

    private static void searchLuceneAndScore(IngredientsMongoClient mongoClient, String query){

        String[] queryArr = query.split(",");
        ArrayList<String> queryList = new ArrayList<String>(Arrays.asList(queryArr));
        IngredientsCleaner ingredientsCleaner = new IngredientsCleaner();
        query = ingredientsCleaner.getQuery(queryList);

        String indexPath = "LuceneIndex";
        LuceneSearcher searcher = new LuceneSearcher();
        ScoringAlgorithm scoringAlgorithm = new ScoringAlgorithm(mongoClient);
        try{
            ArrayList<LuceneResult> results = searcher.Search(indexPath, query, 20);
            if (results.size() > 0) {
                float score = scoringAlgorithm.score(results);
                System.out.println(score);
            }
            else
            {
                System.out.println("-1 Result not found");
            }

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

    private static void runAnalysis(IngredientsMongoClient mongoClient)
    {

        Bson query = QueryBuilder.getAllValidItemsQuery();
        mongoClient.getFoodItems(query, true);
    }
}
