package com.nbp828.mongoDBWrapper;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import com.nbp828.Common.FoodItem;
import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class IngredientsMongoClient {

    MongoClient mongoClient;

    public IngredientsMongoClient()
    {
        this.mongoClient = new MongoClient("localhost", 27017);
    }

    public ArrayList<FoodItem> getFoodItems(Bson query)
    {
        MongoDatabase database = mongoClient.getDatabase("off");
        MongoCollection<Document> collection = database.getCollection("products");
        FindIterable<Document> documents = collection.find(query);
        ArrayList<FoodItem> retItems = new ArrayList<>();
        for (Document document : documents){
            FoodItem f = DocumentToFoodItemConverter.Convert(document);
            retItems.add(f);
        }

        return retItems;
    }
}
