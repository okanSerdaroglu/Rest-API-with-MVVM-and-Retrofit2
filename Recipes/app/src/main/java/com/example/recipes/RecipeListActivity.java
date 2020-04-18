package com.example.recipes;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.recipes.models.Recipe;
import com.example.recipes.requests.RecipeApi;
import com.example.recipes.requests.ServiceGenerator;
import com.example.recipes.requests.responses.RecipeSearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {


    private static final String TAG = "RecipeListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRetrofitRequest();
            }
        });
    }

    private void testRetrofitRequest(){
        RecipeApi recipeApi = ServiceGenerator.getRecipeApi();
        Call<RecipeSearchResponse> recipeSearchResponseCall
                = recipeApi.searchRecipe("chicken breast","1");
        recipeSearchResponseCall.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call,
                                   Response<RecipeSearchResponse> response) {
                Log.d(TAG,"onResponse: server response : " + response.toString());
                if (response.code() == 200){
                    Log.d(TAG,"onResponse: server response : " + response.body().toString());
                    List<Recipe>recipeList = new ArrayList<>(response.body().getRecipeList());
                    for (Recipe recipe : recipeList){
                        Log.d(TAG,"onResponse: " + recipe.getTitle());
                    }
                } else {
                    try {
                        Log.d(TAG,"onResponse: " + response.errorBody().string());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {


            }
        });
    }

}
