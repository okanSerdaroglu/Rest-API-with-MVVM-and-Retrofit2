package com.example.recipes;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
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
        initSearchView();
        if (!recipeListViewModel.isViewingRecipes()){
            displaySearchCategories();
        }
    }

    private void searchRecipesAPI(String query, int pageNumber) {
        recipeListViewModel.searchRecipesAPI(query, pageNumber);
    }

    private void initSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recipeRecyclerAdapter.displayLoading();
                searchRecipesAPI(query, 0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void subscribeObservers() {
        recipeListViewModel.getRecipeList().observe(this, recipes -> {
            if (recipes != null) {
                Testing.printRecipes(recipes, TAG);
                recipeRecyclerAdapter.setRecipes(recipes);
            }
        });
    }

    private void initRecyclerView() {
        recipeRecyclerAdapter = new RecipeRecyclerAdapter(this);
        recyclerViewRecipeList.setAdapter(recipeRecyclerAdapter);
    }


    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

        recipeRecyclerAdapter.displayLoading();
        recipeListViewModel.searchRecipesAPI(category,0);

    }

    private void testRetrofitRequest() {
        searchRecipesAPI("chicken breast", 0);
    }

    private void displaySearchCategories (){
        recipeListViewModel.setViewingRecipes(true);
        recipeRecyclerAdapter.displayCategories();
    }

}
