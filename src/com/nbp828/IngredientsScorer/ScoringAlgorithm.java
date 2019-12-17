package com.nbp828.IngredientsScorer;

import com.nbp828.Common.FoodItem;
import com.nbp828.LuceneWrapper.LuceneResult;
import com.nbp828.mongoDBWrapper.IngredientsMongoClient;
import com.nbp828.mongoDBWrapper.QueryBuilder;
import org.bson.conversions.Bson;

import java.util.ArrayList;

public class ScoringAlgorithm {

    IngredientsMongoClient mongoClient;

    public ScoringAlgorithm(IngredientsMongoClient ingredientsMongoClient){
        this.mongoClient = ingredientsMongoClient;
    }

    public float score(ArrayList<LuceneResult> luceneResults)
    {
        float retValue = 0.0f;
        float normalizer = 0.0f;
        for (LuceneResult luceneResult : luceneResults){
            normalizer += luceneResult.getScore();
        }

        for (LuceneResult luceneResult : luceneResults){
            int nutriScore = getNutritionScore(luceneResult.getCode());
            retValue += (luceneResult.getScore() / normalizer) *
                    ((nutriScore - 12.5f) * -1.0f + 27.5f) *
                    (100.0f/55.0f);
        }

        return retValue;
    }

    private int getNutritionScore(String code){

        Bson query = QueryBuilder.getOneFoodItemQuery(code);
        ArrayList<FoodItem> foodItems = this.mongoClient.getFoodItems(query, false);

        return foodItems.get(0).getScore();
    }
}
