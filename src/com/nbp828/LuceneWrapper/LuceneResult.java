package com.nbp828.LuceneWrapper;

public class LuceneResult {

    private String code;
    private float score;

    public LuceneResult(String code, float score){
        this.code = code;
        this.score = score;
    }

    public String getCode() {
        return code;
    }

    public float getScore() {
        return score;
    }

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
