package com.example.recipes.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipes.models.Recipe;
import com.example.recipes.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeApiClient mRecipeApiClient;
    private String query;
    private int pageNumber;
    private MutableLiveData<Boolean> isQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<Recipe>> mRecipes = new MediatorLiveData<>();
    // mediator live data is a kind of MutableLiveData and it observes another mutable live data changes.

    public MutableLiveData<Boolean> getIsQueryExhausted() {
        return isQueryExhausted;
    }

    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    private RecipeRepository() {
        mRecipeApiClient = RecipeApiClient.getInstance();
        initMediators();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    private void initMediators() {
        LiveData<List<Recipe>> recipeListApiSource = mRecipeApiClient.getRecipes();
        mRecipes.addSource(recipeListApiSource, recipeList -> {
            if (recipeList != null) {
                mRecipes.setValue(recipeList);
                doneQuery(recipeList);
            } else {
                // search database cache
                doneQuery(null);
            }
        });
    }


    public void doneQuery(List<Recipe> list) {
        if (list != null) {
            if (list.size() < 30) {
                isQueryExhausted.setValue(true);
            }
        } else {
            isQueryExhausted.setValue(true);

        }
    }

    public LiveData<Recipe> getRecipe() {
        return mRecipeApiClient.getRecipe();
    }

    public LiveData<Boolean> isRecipeRequestTimeOut() {
        return mRecipeApiClient.isRecipeRequestTimeOut();
    }

    public void searchRecipesAPI(String query, int pageNumber) {
        if (pageNumber == 0) {
            pageNumber = 1;
        }
        this.query = query;
        this.pageNumber = pageNumber;
        isQueryExhausted.setValue(false);
        mRecipeApiClient.searchRecipesApi(query, pageNumber);
    }

    public void searchRecipeByID(String recipeID) {
        mRecipeApiClient.searchRecipeById(recipeID);
    }

    public void searchNextPage() {
        searchRecipesAPI(query, pageNumber + 1);
    }

    public void cancelRequest() {
        mRecipeApiClient.cancelRequest();
    }


}
