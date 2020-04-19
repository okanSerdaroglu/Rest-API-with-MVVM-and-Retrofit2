package com.example.recipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipes.models.Recipe;

import java.util.List;

/**  if you need application class here
     you have to extend your ViewModel class
     from AndroidViewModel which have a constructor
     with an application instance */


public class RecipeListViewModel extends ViewModel {

    public RecipeListViewModel(){}

    private MutableLiveData<List<Recipe>> recipeList = new MutableLiveData<>();

    public LiveData<List<Recipe>> getRecipeList() {
        return recipeList;
    }

}
