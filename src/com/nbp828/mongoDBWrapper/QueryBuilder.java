package com.nbp828.mongoDBWrapper;

import org.bson.conversions.*;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.*;

public class QueryBuilder {

    public static Bson getAllValidItemsQuery(){

        // product
        Bson productNameQuery = exists("product_name");
        Bson brandNameQuery = exists("brands");
        ArrayList<Bson> orQueries = new ArrayList<>();
        orQueries.add(productNameQuery);
        orQueries.add(brandNameQuery);
        Bson productQuery = or(orQueries);

        // score
        Bson gradeQuery = exists("nutriscore_grade");
        Bson nutritionScoreQuery = exists("nutriscore_score");
        orQueries = new ArrayList<>();
        orQueries.add(gradeQuery);
        orQueries.add(nutritionScoreQuery);
        Bson scoreQuery = or(orQueries);

        // code
        Bson codeQuery = exists("code");

        // categories
        Bson categoriesQuery = exists("categories");

        // ingredients
        Bson ingredientsQuery = exists("ingredients_hierarchy");

        ArrayList<Bson> andQueries = new ArrayList<>();
        andQueries.add(productQuery);
        andQueries.add(scoreQuery);
        andQueries.add(codeQuery);
        andQueries.add(categoriesQuery);
        andQueries.add(ingredientsQuery);

        Bson ret = and(andQueries);
        return ret;
    }

    public static Bson getOneFoodItemQuery(String code){
        // {"code": f"{code}"}
        Bson ret = eq("code", code);
        return ret;
    }

    public static Bson getCategoryQuery(String category){
        return null;
    }

}
