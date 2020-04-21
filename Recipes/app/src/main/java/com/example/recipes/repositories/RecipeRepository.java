package com.example.recipes.repositories;

import androidx.lifecycle.LiveData;
import com.example.recipes.models.Recipe;
import com.example.recipes.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeApiClient mRecipeApiClient;
    private String query;
    private int pageNumber;

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

    public LiveData<Recipe> getRecipe() {
        return mRecipeApiClient.getRecipe();
    }

    public void searchRecipesAPI(String query, int pageNumber){
        if (pageNumber == 0){
            pageNumber = 1;
        }
        this.query = query;
        this.pageNumber = pageNumber;
        mRecipeApiClient.searchRecipesApi(query,pageNumber);
    }

    public void searchRecipeByID (String recipeID){
        mRecipeApiClient.searchRecipeById(recipeID);
    }

    public void searchNextPage (){
        searchRecipesAPI(query,pageNumber+1);
    }

    public void cancelRequest (){
        mRecipeApiClient.cancelRequest();
    }


}
