package com.example.recipes.requests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipes.AppExecutors;
import com.example.recipes.models.Recipe;
import com.example.recipes.utils.Constants;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RecipeApiClient {

    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mRecipes;

    public static RecipeApiClient getInstance() {
        if (instance == null) {
            instance = new RecipeApiClient();
        }
        return instance;
    }

    private RecipeApiClient() {
        mRecipes = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    public void searchRecipesApi() {
        final Future handler = AppExecutors.getInstance().networkIO().submit(() -> {
            // retrieve data from rest API
            // mRecipes.postValue();
        });
        AppExecutors.getInstance().networkIO().schedule(() -> {
            // let the user know its time out
            handler.cancel(true);

        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

}
