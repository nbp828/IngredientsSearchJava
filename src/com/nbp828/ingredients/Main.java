package com.nbp828.ingredients;

import com.nbp828.LuceneWrapper.LuceneDataDirectoryBuilder;
import com.nbp828.mongoDBWrapper.IngredientsMongoClient;

public class Main {

    public static void main(String[] args) {
	// write your code here

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
