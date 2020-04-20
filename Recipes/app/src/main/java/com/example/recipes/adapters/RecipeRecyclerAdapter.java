package com.example.recipes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipes.R;
import com.example.recipes.models.Recipe;
import com.example.recipes.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;

    private List<Recipe> mRecipes;
    private OnRecipeListener recipeListener;

    public RecipeRecyclerAdapter(OnRecipeListener recipeListener) {

        this.recipeListener = recipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == LOADING_TYPE) {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.layout_loading_list_item, parent, false);
            return new LoadingViewHolder(view);
        } else if (viewType == CATEGORY_TYPE) {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.layout_category_list_item, parent, false);
            return new CategoryViewHolder(view, recipeListener);

        }
        view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(view, recipeListener);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        int itemViewType = getItemViewType(position);
        if (itemViewType == RECIPE_TYPE) {
            ((RecipeViewHolder) holder).onBind(recipe);
        } else if (itemViewType == CATEGORY_TYPE) {
            ((CategoryViewHolder) holder).onBind(recipe);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mRecipes.get(position).getSocial_rank() == -1) {
            return CATEGORY_TYPE;
        } else if (mRecipes.get(position).getTitle().equals("LOADING...")) {
            return LOADING_TYPE;
        } else if (position == mRecipes.size() - 1
                && position != 0
                && !mRecipes.get(position).getTitle().equals("EXHAUSTED...")) {
            return LOADING_TYPE;
        } else {
            return RECIPE_TYPE;
        }
    }

    public void displayCategories() {
        List<Recipe> categories = new ArrayList<>();
        for (int i = 0; i < Constants.DEFAULT_SEARCH_CATEGORIES.length; i++) {
            Recipe recipe = new Recipe();
            recipe.setTitle(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            recipe.setImage_url(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]);
            recipe.setSocial_rank(-1);
            categories.add(recipe);
        }
        mRecipes = categories;
        notifyDataSetChanged();
    }

    private boolean isLoading() {
        if (mRecipes != null
                && mRecipes.size() > 0) {
            return mRecipes.get(mRecipes.size() - 1).getTitle().equals("LOADING...");
        }
        return false;
    }

    public void displayLoading() {
        if (!isLoading()) {
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING...");
            List<Recipe> loadingList = new ArrayList<>();
            loadingList.add(recipe);
            mRecipes = loadingList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (mRecipes != null) {
            return mRecipes.size();
        } else {
            return 0;
        }
    }

    public void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    public Recipe getSelectedRecipe (int position){
        if (mRecipes != null && mRecipes.size()>0){
            return mRecipes.get(position);
        }
        return null;
    }

}
