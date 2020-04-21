package com.example.recipes.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipes.AppExecutors;
import com.example.recipes.models.Recipe;
import com.example.recipes.requests.responses.RecipeResponse;
import com.example.recipes.requests.responses.RecipeSearchResponse;
import com.example.recipes.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class RecipeApiClient {

    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mRecipes;
    private MutableLiveData<Recipe> recipe;


    private static final String TAG = "RecipeApiClient";
    private RetrieveRecipesRunnable mRetrieveRecipesRunnable;
    private RetrieveRecipeRunnable mRetrieveRecipeRunnable;

    public static RecipeApiClient getInstance() {
        if (instance == null) {
            instance = new RecipeApiClient();
        }
        return instance;
    }

    private RecipeApiClient() {
        mRecipes = new MutableLiveData<>();
        recipe = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    public MutableLiveData<Recipe> getRecipe() {
        return recipe;
    }

    public void searchRecipesApi(String query, int pageNumber) {
        if (mRetrieveRecipesRunnable != null) {
            mRetrieveRecipesRunnable = null;
        }
        mRetrieveRecipesRunnable = new RetrieveRecipesRunnable(query, pageNumber);

        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveRecipesRunnable);

        AppExecutors.getInstance().networkIO().schedule(() -> {
            // let the user know its time out
            handler.cancel(true);

        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void searchRecipeById(String recipeID){
        if (mRetrieveRecipeRunnable != null){
            mRetrieveRecipeRunnable = null;
        }
        mRetrieveRecipeRunnable = new RetrieveRecipeRunnable(recipeID);
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveRecipeRunnable);

        AppExecutors.getInstance().networkIO().schedule(() -> {
            // let the user know it is time out
            handler.cancel(true);
        },Constants.NETWORK_TIMEOUT,TimeUnit.MILLISECONDS);

    }

    private class RetrieveRecipesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveRecipesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipes(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<Recipe> list =
                            new ArrayList<>(((RecipeSearchResponse) response.body()).getRecipeList());
                    if (pageNumber == 1) {
                        // we used postValue method because we are in background thread.
                        // if we are in UI thread we have to use setValue method
                        mRecipes.postValue(list);
                    } else {
                        List<Recipe> currentRecipes = mRecipes.getValue();
                        currentRecipes.addAll(list);
                        mRecipes.postValue(currentRecipes);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run : " + error);
                    mRecipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mRecipes.postValue(null);
            }

        }

        private Call<RecipeSearchResponse> getRecipes(String query, int pageNumber) {
            return ServiceGenerator.getRecipeApi().searchRecipe(query, String.valueOf(pageNumber));
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest : cancelling the search or request");
            cancelRequest = true;
        }

    }

    private class RetrieveRecipeRunnable implements Runnable {

        private String recipeID;
        boolean cancelRequest;

        public RetrieveRecipeRunnable(String recipeID) {
            cancelRequest = false;
            this.recipeID = recipeID;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipe(recipeID).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    Recipe recipe = ((RecipeResponse) response.body()).getRecipe();
                    RecipeApiClient.this.recipe.postValue(recipe);
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run : " + error);
                    recipe.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                recipe.postValue(null);
            }

        }

        private Call<RecipeResponse> getRecipe(String recipeID) {
            return ServiceGenerator.getRecipeApi().getRecipe(recipeID);
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest : cancelling the search or request");
            cancelRequest = true;
        }

    }


    public void cancelRequest() {
        if (mRetrieveRecipesRunnable != null) {
            mRetrieveRecipesRunnable.cancelRequest();
        }

        if (mRetrieveRecipeRunnable != null) {
            mRetrieveRecipeRunnable.cancelRequest();
        }

    }

}
