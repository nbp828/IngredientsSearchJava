package com.nbp828.Common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

public class IngredientsCleaner {

    HashSet<String> stopWords = new HashSet<>();

    public IngredientsCleaner() {

        String filepath = "lemur-stopwords.txt";

        try {

            File file = new File(filepath);
            FileReader reader = new FileReader(file);

            BufferedReader br = new BufferedReader(reader);

            String st;
            while ((st = br.readLine()) != null) {
                st = st.trim();
                this.stopWords.add(st);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }

    public String getQuery(Iterable<String> ingredients){
        ArrayList<String> cleanIngredients = getCleanIngredients(ingredients);
        StringBuilder sb = new StringBuilder();

        for (String item : cleanIngredients)
        {
            sb.append(item);
            sb.append(" ");
        }

        return sb.toString();
    }

    public ArrayList<String> getCleanIngredients(Iterable<String> ingredients){
        ArrayList<String> retList = new ArrayList<>();
        for (String ingredient : ingredients){
            String retIngredient = ingredient;

            // trim
            retIngredient = retIngredient.trim();

            // lower
            retIngredient = retIngredient.toLowerCase();

            // remove language
            retIngredient = removeStartingLanguage(retIngredient);

            // remove chars
            retIngredient = removeChars(retIngredient);

            // remove stop words
            retIngredient = removeStopWord(retIngredient);

            if (retIngredient.length() > 0) {
                retList.add(retIngredient);
            }
        }

        return retList;
    }

    private String removeStopWord(String ingredient){

        String[] words = ingredient.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words){
            if (!this.stopWords.contains(word)){
                sb.append(word);
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    private String removeChars(String ingredient){
        ingredient = ingredient.toLowerCase();

        StringBuilder sb = new StringBuilder();
        for (char c : ingredient.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                sb.append(c);
            } else if (c >= '0' && c <= '9') {
                sb.append(c);
            } else if (c == ' '
                    || c == '-'
                    || c == '_'
                    || c == ','
                    || c == ';'
                    || c == '('
                    || c == ')'
                    || c == '{'
                    || c == '}'
                    || c == '.'
                    || c == '!'
                    || c == '*') {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    private String removeStartingLanguage(String ingredient){
        if (ingredient.length() >= 3 && ingredient.charAt(2) == ':') {
            ingredient = ingredient.substring(3);
        }

        return ingredient;
    }
}
