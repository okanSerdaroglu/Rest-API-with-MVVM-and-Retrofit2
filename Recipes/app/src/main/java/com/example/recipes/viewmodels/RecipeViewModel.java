package com.example.recipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipes.models.Recipe;
import com.example.recipes.repositories.RecipeRepository;

public class RecipeViewModel extends ViewModel {

    private RecipeRepository recipeRepository;
    private String recipeID;
    private boolean didRetrieveRecipe;

    public boolean isDidRetrieveRecipe() {
        return didRetrieveRecipe;
    }

    public void setDidRetrieveRecipe(boolean didRetrieveRecipe) {
        this.didRetrieveRecipe = didRetrieveRecipe;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public RecipeViewModel() {
        recipeRepository = RecipeRepository.getInstance();
        didRetrieveRecipe = false;
    }

    public LiveData<Recipe> getRecipe() {
        return recipeRepository.getRecipe();
    }

    public void searchRecipeById(String recipeID) {
        this.recipeID = recipeID;
        recipeRepository.searchRecipeByID(recipeID);
    }

    public LiveData<Boolean> isRecipeRequestTimeOut() {
        return recipeRepository.isRecipeRequestTimeOut();
    }


}
