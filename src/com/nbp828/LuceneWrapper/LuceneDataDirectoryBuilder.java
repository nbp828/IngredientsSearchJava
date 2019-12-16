package com.nbp828.LuceneWrapper;

import com.nbp828.Common.FoodItem;
import com.nbp828.mongoDBWrapper.IngredientsMongoClient;
import com.nbp828.mongoDBWrapper.QueryBuilder;
import org.bson.conversions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class LuceneDataDirectoryBuilder {

    IngredientsMongoClient mongoClient;

    public LuceneDataDirectoryBuilder(IngredientsMongoClient mongoClient)
    {
        this.mongoClient = mongoClient;
    }

    public void buildDirectory(String luceneInputDirectoryPath) throws IOException {
        // Bson query = QueryBuilder.getAllValidItemsQuery();
        Bson query = QueryBuilder.getOneFoodItemQuery("0013764027282");
        ArrayList<FoodItem> items = mongoClient.getFoodItems(query);
        FoodItem item = items.get(0);

        String data = item.getIngredients().toString();
        var path = Paths.get(luceneInputDirectoryPath, item.getCode());
        Files.write(path, data.getBytes(), StandardOpenOption.CREATE);
    }
}
