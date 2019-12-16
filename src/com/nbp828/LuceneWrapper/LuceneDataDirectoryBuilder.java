package com.nbp828.LuceneWrapper;

import com.nbp828.mongoDBWrapper.IngredientsMongoClient;
import com.nbp828.mongoDBWrapper.QueryBuilder;
import org.bson.conversions.*;

public class LuceneDataDirectoryBuilder {

    IngredientsMongoClient mongoClient;

    public LuceneDataDirectoryBuilder(IngredientsMongoClient mongoClient)
    {
        this.mongoClient = mongoClient;
    }

    public void buildDirectory(String luceneInputDirectoryPath){
        Bson query = QueryBuilder.getAllValidItemsQuery();
         mongoClient.getDocuments(query);
    }
}
