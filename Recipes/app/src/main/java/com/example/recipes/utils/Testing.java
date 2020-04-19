package com.example.recipes.utils;

import android.util.Log;

import com.example.recipes.models.Recipe;

import java.util.List;

public class Testing {

    public static void printRecipes(List<Recipe> recipeList, String tag) {
        if (recipeList != null) {
            for (Recipe recipe : recipeList) {
                Log.d(tag, "onChanged : " + recipe.getTitle());
            }
        }
    }
}
