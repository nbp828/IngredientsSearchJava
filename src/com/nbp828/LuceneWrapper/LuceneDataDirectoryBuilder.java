package com.nbp828.LuceneWrapper;

import com.nbp828.Common.FoodItem;
import com.nbp828.mongoDBWrapper.IngredientsMongoClient;
import com.nbp828.mongoDBWrapper.QueryBuilder;
import org.bson.conversions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        Bson query = QueryBuilder.getAllValidItemsQuery();
        // Bson query = QueryBuilder.getOneFoodItemQuery("0013764027282");
        ArrayList<FoodItem> items = mongoClient.getFoodItems(query, true);
        System.out.println("Writing to Lucene Input Directory...");

        // Check if directory exist
        if (!Files.exists(Paths.get(luceneInputDirectoryPath))) {
            Files.createDirectory(Paths.get(luceneInputDirectoryPath));
        }

        for (FoodItem item : items)
        {
            String data = item.getIngredientsString();
            Path path = Paths.get(luceneInputDirectoryPath, item.getCode());
            Files.deleteIfExists(path);
            Files.write(path, data.getBytes(), StandardOpenOption.CREATE);
        }
    }
}
