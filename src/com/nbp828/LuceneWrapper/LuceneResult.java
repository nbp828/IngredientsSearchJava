package com.nbp828.LuceneWrapper;

public class LuceneResult {

    private String code;
    private float score;
    private int nutritionScore;

    public LuceneResult(String code, float score, int nutritionScore){
        this.code = code;
        this.score = score;
        this.nutritionScore = nutritionScore;
    }

    public String getCode() {
        return code;
    }

    public float getScore() {
        return score;
    }

    public int getNutritionScore() { return nutritionScore; }

    public String resultToString(){
        StringBuilder sb = new StringBuilder();
        // code
        sb.append("{code: \"");
        sb.append(code);
        sb.append("\", ");
        // score
        sb.append("score: ");
        sb.append(score);
        sb.append("}");

        return sb.toString();
    }
}
