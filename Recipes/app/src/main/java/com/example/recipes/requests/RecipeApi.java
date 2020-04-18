package com.example.recipes.requests;


import com.example.recipes.requests.responses.RecipeResponse;
import com.example.recipes.requests.responses.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {

    // search method
    @GET("api/search")
    Call<RecipeSearchResponse> searchRecipe(
            @Query("key") String key,
            @Query("q") String query,
            @Query("page") String page
    );

    // get recipe request
    @GET("api/get")
    Call<RecipeResponse>getRecipe(
            @Query("key") String key,
            @Query("rId") String recipeId
    );

}
