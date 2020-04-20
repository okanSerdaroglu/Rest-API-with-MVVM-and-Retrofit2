package com.example.recipes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipes.R;
import com.example.recipes.models.Recipe;

import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Recipe>mRecipes;
    private OnRecipeListener recipeListener;

    public RecipeRecyclerAdapter(OnRecipeListener recipeListener) {

        this.recipeListener = recipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recipe_list_item,parent,false);
        return new RecipeViewHolder(view,recipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        ((RecipeViewHolder)holder).onBind(recipe);
    }

    @Override
    public int getItemCount() {
        if (mRecipes != null){
            return mRecipes.size();
        } else {
            return 0;
        }
    }

    public void setRecipes (List<Recipe>recipes){
        mRecipes = recipes;
        notifyDataSetChanged();
    }

}
