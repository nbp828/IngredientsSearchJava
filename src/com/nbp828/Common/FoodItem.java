package com.nbp828.Common;

public class FoodItem {

    String code;
    String name;
    Integer score;
    Iterable<String> categories;
    Iterable<String> ingredients;


    public FoodItem(String code,
                    String name,
                    Integer score,
                    Iterable<String> categories,
                    Iterable<String> ingredients)
    {
        this.code = code;
        this.name = name;
        this.score = score;
        this.categories = categories;
        this.ingredients = ingredients;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public Integer getScore() {
        return this.score;
    }

    public Iterable<String> getCategories() {
        return categories;
    }

    public Iterable<String> getIngredients() {
        return ingredients;
    }
}
