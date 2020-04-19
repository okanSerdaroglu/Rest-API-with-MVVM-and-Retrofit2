package com.example.recipes.adapters;

import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipes.R;
import com.example.recipes.models.Recipe;

public class RecipeViewHolder
        extends RecyclerView.ViewHolder
        implements DialogInterface.OnClickListener {

    private TextView textViewTitle;
    private TextView textViewPublisher;
    private TextView textViewSocialScore;
    private AppCompatImageView image;
    private OnRecipeListener recipeListener;

    RecipeViewHolder(@NonNull View itemView, OnRecipeListener recipeListener) {
        super(itemView);
        this.recipeListener = recipeListener;
        textViewTitle = itemView.findViewById(R.id.recipe_title);
        textViewPublisher = itemView.findViewById(R.id.recipe_publisher);
        textViewSocialScore = itemView.findViewById(R.id.recipe_social_score);
        image = itemView.findViewById(R.id.recipe_image);
    }

    void onBind (Recipe recipe){
        textViewTitle.setText(recipe.getTitle());
        textViewPublisher.setText(recipe.getPublisher());
        textViewSocialScore.setText(String.valueOf(recipe.getSocial_rank()));
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        recipeListener.onRecipeClick(getAdapterPosition());
    }
}
