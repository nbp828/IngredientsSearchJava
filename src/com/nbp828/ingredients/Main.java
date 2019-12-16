package com.nbp828.ingredients;

import com.nbp828.LuceneWrapper.LuceneDataDirectoryBuilder;
import com.nbp828.mongoDBWrapper.IngredientsMongoClient;

import java.nio.charset.Charset;

public class Main {

    public static void main(String[] args) {
        CreateLuceneInputDirectory();
    }

    private static void CreateLuceneInputDirectory() {
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
