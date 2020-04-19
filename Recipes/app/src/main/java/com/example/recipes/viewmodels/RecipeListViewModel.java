package com.example.recipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipes.models.Recipe;
import com.example.recipes.repositories.RecipeRepository;

import java.util.List;

/**  if you need application class here
     you have to extend your ViewModel class
     from AndroidViewModel which have a constructor
     with an application instance */


public class RecipeListViewModel extends ViewModel {

    private RecipeRepository recipeRepository;

    public RecipeListViewModel(){
        recipeRepository = RecipeRepository.getInstance();
    }
    public LiveData<List<Recipe>> getRecipeList() {
        return recipeRepository.getRecipes();
    }

}
