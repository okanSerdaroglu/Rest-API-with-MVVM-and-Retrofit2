package com.example.recipes.repositories;

import androidx.lifecycle.LiveData;
import com.example.recipes.models.Recipe;
import com.example.recipes.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeApiClient mRecipeApiClient;

    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    private RecipeRepository() {

        mRecipeApiClient = RecipeApiClient.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipeApiClient.getRecipes();
    }

    public void searchRecipesAPI(String query, int pageNumber){
        if (pageNumber == 0){
            pageNumber = 1;
        }
        mRecipeApiClient.searchRecipesApi(query,pageNumber);
    }


}
