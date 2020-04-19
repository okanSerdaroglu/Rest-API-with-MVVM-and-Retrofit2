package com.example.recipes;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.example.recipes.models.Recipe;
import com.example.recipes.viewmodels.RecipeListViewModel;

public class RecipeListActivity extends BaseActivity {

    private static final String TAG = "RecipeListActivity";
    private RecipeListViewModel recipeListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);
        subscribeObservers();
        findViewById(R.id.test).setOnClickListener(v -> {
            testRetrofit();
        });
    }

    private void searchRecipesAPI(String query, int pageNumber) {
        recipeListViewModel.searchRecipesAPI(query, pageNumber);
    }

    private void subscribeObservers() {
        recipeListViewModel.getRecipeList().observe(this, recipes -> {
            if (recipes != null && recipes.size() > 0) {
                for (Recipe recipe : recipes) {
                    Log.d(TAG, "onChanged" + recipe.getTitle());
                }
            }
        });
    }

    private void testRetrofit() {
        searchRecipesAPI("chicken breast", 0);
    }

}
