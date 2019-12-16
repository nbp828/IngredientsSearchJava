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

    public String getCategoriesString() {
        StringBuilder sb = new StringBuilder();
        for (String categories : this.categories){
            sb.append(categories);
            sb.append(" ");
        }

        return sb.toString();
    }

    public String getIngredientsString() {
        StringBuilder sb = new StringBuilder();
        for (String ingredient : this.ingredients){
            sb.append(ingredient);
            sb.append(" ");
        }

        return sb.toString();
    }



    @Override
    public String toString()
    {
        /*
            String code;
            String name;
            Integer score;
            Iterable<String> categories;
            Iterable<String> ingredients;
         */

        StringBuilder sb = new StringBuilder();
        sb.append("{");

        // code
        sb.append("code: ");
        sb.append("\"");
        sb.append(this.code);
        sb.append("\"");
        sb.append(",");

        // name
        sb.append("name: ");
        sb.append("\"");
        sb.append(this.name);
        sb.append("\"");
        sb.append(",");

        // score
        sb.append("score: ");
        sb.append(this.score);
        sb.append(",");

        // score
        sb.append("ingredients: ");
        sb.append("\"");
        sb.append(this.getIngredientsString());
        sb.append("\"");
        sb.append(",");

        // score
        sb.append("categories: ");
        sb.append("\"");
        sb.append(this.getCategoriesString());
        sb.append("\"");

        sb.append("}");

        return sb.toString();
    }
}
