package com.nbp828.Common;

import java.util.ArrayList;

public class IngredientsCleaner {

    public static ArrayList<String> getCleanIngredients(ArrayList<String> ingredients){
        ArrayList<String> ret = new ArrayList<>();

        // remove en or fr
        for (String ingredient : ingredients){
            String retIngredient = ingredient.stripLeading();
            if (retIngredient.startsWith("en:") || retIngredient.startsWith("fr:")){
                retIngredient = retIngredient.substring(3);
            }

            // TODO: remove water and organic

            // Only [a-z,A-Z,0-9]
            StringBuilder sb = new StringBuilder();
            retIngredient = retIngredient.toLowerCase();
            for (char c : retIngredient.toCharArray()){
                if (c >= 'a' && c <= 'z'){
                    sb.append(c);
                }
                else if (c >= '0' && c <= '9') {
                    sb.append(c);
                }
                else if (c == ' '){
                    sb.append(c);
                }
            }

            if (sb.length() > 0) {
                ret.add(sb.toString());
            }
        }

        return ret;
    }
}
