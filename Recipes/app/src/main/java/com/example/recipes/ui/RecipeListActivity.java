package com.example.recipes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipes.R;
import com.example.recipes.adapters.OnRecipeListener;
import com.example.recipes.adapters.RecipeRecyclerAdapter;
import com.example.recipes.utils.Testing;
import com.example.recipes.utils.VerticalSpacingItemDecorator;
import com.example.recipes.viewmodels.RecipeListViewModel;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    private static final String TAG = "RecipeListActivity";
    private RecipeListViewModel recipeListViewModel;
    private RecyclerView recyclerViewRecipeList;
    private RecipeRecyclerAdapter recipeRecyclerAdapter;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);

        recyclerViewRecipeList = findViewById(R.id.recyclerView_recipe_list);
        initRecyclerView();

        subscribeObservers();
        initSearchView();
        if (!recipeListViewModel.isViewingRecipes()) {
            displaySearchCategories();
        }

        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private void searchRecipesAPI(String query, int pageNumber) {
        recipeListViewModel.searchRecipesAPI(query, pageNumber);
    }

    private void initSearchView() {
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recipeRecyclerAdapter.displayLoading();
                searchRecipesAPI(query, 0);
                searchView.clearFocus();
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
                if (recipeListViewModel.isViewingRecipes()) {
                    Testing.printRecipes(recipes, TAG);
                    recipeListViewModel.setPerformingQuery(false);
                    recipeRecyclerAdapter.setRecipes(recipes);
                }
            }
        });

        recipeListViewModel.isQueryExhausted().observe(this, aBoolean -> {
             if (aBoolean) {
                 Log.d(TAG,"onChanged : exhausted query");
             }
        });
    }

    private void initRecyclerView() {
        recipeRecyclerAdapter = new RecipeRecyclerAdapter(this);
        recyclerViewRecipeList.setAdapter(recipeRecyclerAdapter);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator
                = new VerticalSpacingItemDecorator(30);
        recyclerViewRecipeList.addItemDecoration(verticalSpacingItemDecorator);
        recyclerViewRecipeList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerViewRecipeList.canScrollVertically(1)) {
                    recipeListViewModel.searchNextPage();
                }
            }
        });
    }


    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(RecipeListActivity.this, RecipeActivity.class);
        intent.putExtra("recipe", recipeRecyclerAdapter.getSelectedRecipe(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

        recipeRecyclerAdapter.displayLoading();
        recipeListViewModel.searchRecipesAPI(category, 0);
        searchView.clearFocus();

    }

    private void testRetrofitRequest() {
        searchRecipesAPI("chicken breast", 0);
    }

    private void displaySearchCategories() {
        recipeListViewModel.setViewingRecipes(false);
        recipeRecyclerAdapter.displayCategories();
    }

    @Override
    public void onBackPressed() {
        if (recipeListViewModel.onBackPressed()) {
            super.onBackPressed();
        } else {
            displaySearchCategories();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_categories) {
            displaySearchCategories();
        }
        return super.onOptionsItemSelected(item);
    }
}
