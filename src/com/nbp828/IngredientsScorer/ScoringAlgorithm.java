package com.nbp828.IngredientsScorer;

import com.nbp828.Common.FoodItem;
import com.nbp828.LuceneWrapper.LuceneResult;
import com.nbp828.mongoDBWrapper.QueryBuilder;
import org.bson.conversions.Bson;

import java.util.ArrayList;

public class ScoringAlgorithm {

    public float score(ArrayList<LuceneResult> luceneResults)
    {
        // System.out.println("results: " + luceneResults.size());
        if (luceneResults.size() == 0){
            return Float.NEGATIVE_INFINITY;
        }

        float retValue = 0.0f;
        float normalizer = 0.0f;
        for (LuceneResult luceneResult : luceneResults){
            normalizer += luceneResult.getScore();
        }

        for (LuceneResult luceneResult : luceneResults){
            int nutriScore = luceneResult.getNutritionScore() - 15;
            retValue += (luceneResult.getScore() / normalizer) *
                    ((nutriScore - 12.5f) * -1.0f + 27.5f) *
                    (100.0f/55.0f);
        }

        return retValue;
    }
}
