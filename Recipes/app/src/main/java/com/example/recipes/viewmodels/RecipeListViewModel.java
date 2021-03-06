package com.example.recipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipes.models.Recipe;
import com.example.recipes.repositories.RecipeRepository;

import java.util.List;

/**
 * if you need application class here
 * you have to extend your ViewModel class
 * from AndroidViewModel which have a constructor
 * with an application instance
 */


public class RecipeListViewModel extends ViewModel {

    private RecipeRepository recipeRepository;
    private boolean isViewingRecipes = false;

    public boolean isPerformingQuery() {
        return isPerformingQuery;
    }

    public LiveData<Boolean> isQueryExhausted() {
        return recipeRepository.getIsQueryExhausted();
    }

    public void setPerformingQuery(boolean performingQuery) {
        isPerformingQuery = performingQuery;
    }

    private boolean isPerformingQuery;

    public RecipeListViewModel() {
        recipeRepository = RecipeRepository.getInstance();
        isPerformingQuery = false;
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return recipeRepository.getRecipes();
    }

    public void searchRecipesAPI(String query, int pageNumber) {
        isPerformingQuery = true;
        isViewingRecipes = true;
        recipeRepository.searchRecipesAPI(query, pageNumber);
    }

    public boolean isViewingRecipes() {
        return isViewingRecipes;
    }

    public void setViewingRecipes(boolean viewingRecipes) {
        this.isViewingRecipes = viewingRecipes;
    }

    public boolean onBackPressed() {
        if (isPerformingQuery) {
            recipeRepository.cancelRequest();
            isPerformingQuery = false;
        }
        if (isViewingRecipes) {
            isViewingRecipes = false;
            return false;
        }
        return true;
    }

    public void searchNextPage() {
        /** if there is not any working query and recyclerView shows recipes */
        if (!isPerformingQuery
                && isViewingRecipes
                && !isQueryExhausted().getValue()) {
            recipeRepository.searchNextPage();
        }
    }


}
