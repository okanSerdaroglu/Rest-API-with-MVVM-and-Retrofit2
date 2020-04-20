package com.example.recipes;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipes.adapters.OnRecipeListener;
import com.example.recipes.adapters.RecipeRecyclerAdapter;
import com.example.recipes.utils.Testing;
import com.example.recipes.viewmodels.RecipeListViewModel;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    private static final String TAG = "RecipeListActivity";
    private RecipeListViewModel recipeListViewModel;
    private RecyclerView recyclerViewRecipeList;
    private RecipeRecyclerAdapter recipeRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);

        recyclerViewRecipeList = findViewById(R.id.recyclerView_recipe_list);
        initRecyclerView();

        subscribeObservers();
        testRetrofitRequest();
    }

    private void searchRecipesAPI(String query, int pageNumber) {
        recipeListViewModel.searchRecipesAPI(query, pageNumber);
    }

    private void subscribeObservers() {
        recipeListViewModel.getRecipeList().observe(this, recipes -> {
            if (recipes != null){
                Testing.printRecipes(recipes,TAG);
                recipeRecyclerAdapter.setRecipes(recipes);
            }
        });
    }

    private void initRecyclerView(){
        recipeRecyclerAdapter = new RecipeRecyclerAdapter(this);
        recyclerViewRecipeList.setAdapter(recipeRecyclerAdapter);
    }


    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }

    private void testRetrofitRequest (){
        searchRecipesAPI("chicken breast",0);
    }
}
