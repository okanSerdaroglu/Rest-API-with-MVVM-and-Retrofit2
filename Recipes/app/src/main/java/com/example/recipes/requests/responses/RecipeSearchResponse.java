package com.example.recipes.requests.responses;

import com.example.recipes.models.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeSearchResponse {

    @SerializedName("count")
    @Expose()
    private int count;

    @SerializedName("recipes")
    @Expose
    private List<Recipe>recipeList;

    public int getCount() {
        return count;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    @Override
    public String toString() {
        return "RecipeSearchResponse{" +
                "count=" + count +
                ", recipeList=" + recipeList +
                '}';
    }
}
