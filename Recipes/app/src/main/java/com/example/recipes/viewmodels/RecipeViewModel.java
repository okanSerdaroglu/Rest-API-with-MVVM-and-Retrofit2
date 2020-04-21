package com.example.recipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipes.models.Recipe;
import com.example.recipes.repositories.RecipeRepository;

public class RecipeViewModel extends ViewModel {

    private RecipeRepository recipeRepository;

    public RecipeViewModel(){
        recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<Recipe> getRecipe (){
        return recipeRepository.getRecipe();
    }

    public void searchRecipeById (String recipeID){
        recipeRepository.searchRecipeByID(recipeID);
    }

}
