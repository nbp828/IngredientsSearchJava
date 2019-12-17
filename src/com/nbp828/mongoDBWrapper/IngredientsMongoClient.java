package com.nbp828.mongoDBWrapper;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import com.nbp828.Common.FoodItem;
import org.bson.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;

import javax.imageio.IIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IngredientsMongoClient {

    MongoClient mongoClient;

    public IngredientsMongoClient()
    {
        this.mongoClient = new MongoClient("localhost", 27017);
    }

    public ArrayList<FoodItem> getFoodItems(Bson query, boolean runAnalysis)
    {
        MongoDatabase database = mongoClient.getDatabase("off");
        MongoCollection<Document> collection = database.getCollection("products");
        // System.out.println("Starting a query...");
        FindIterable<Document> documents = collection.find(query);
        ArrayList<FoodItem> retItems = new ArrayList<>();
        // System.out.println("Starting to convert documents...");
        for (Document document : documents){
            FoodItem f = DocumentToFoodItemConverter.Convert(document);
            if (f != null) {
                retItems.add(f);
            }
        }

        if (runAnalysis){
            try{
                this.runAnalysis(retItems);
            }
            catch (Exception e)
            {
                System.out.println("Analysis failed, " + e.toString());
            }
        }

        return retItems;
    }

    private void runAnalysis(ArrayList<FoodItem> foodItems) throws IOException {
        System.out.println("Analyzing...");
        StringBuilder sb = new StringBuilder();

        // count the number of items
        sb.append("Items: " + foodItems.size());
        sb.append('\n');

        // lowest and highest score
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (FoodItem item : foodItems){
            if (item.getScore() < min){
                min = item.getScore();
            }
            if (item.getScore() > max){
                max = item.getScore();
            }
        }

        sb.append("Max Score: " + max);
        sb.append('\n');

        sb.append("Min Score: " + min);
        sb.append('\n');

        // random test data
        sb.append("Random Test Data: \n");

        int randomCount = 100 < foodItems.size() ? 100 : foodItems.size();
        Random random = new Random();
        for (int i = 0; i < randomCount; i++){
            int randomInteger = random.nextInt(foodItems.size());
            sb.append(foodItems.get(randomInteger).toString() + '\n');
        }

        String data = sb.toString();
        LocalDateTime time = LocalDateTime.now();
        String filename = "Analysis" + time.getHour() + "-" + time.getMinute() + "-" + time.getSecond() + ".log";
        Path path = Paths.get("Logs", filename);
        Files.deleteIfExists(path);
        Files.write(path, data.getBytes(), StandardOpenOption.CREATE);
    }
}
