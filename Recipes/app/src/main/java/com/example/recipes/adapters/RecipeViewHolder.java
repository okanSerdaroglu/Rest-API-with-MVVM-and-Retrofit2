package com.example.recipes.adapters;

import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

    void onBind(Recipe recipe) {

        RequestOptions requestOptions = new RequestOptions().
                placeholder(R.drawable.ic_launcher_background);
        Glide.with(image.getContext()).
                setDefaultRequestOptions(requestOptions).load(recipe.getImage_url()).into(image);

        textViewTitle.setText(recipe.getTitle());
        textViewPublisher.setText(recipe.getPublisher());
        textViewSocialScore.setText(String.valueOf(recipe.getSocial_rank()));
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        recipeListener.onRecipeClick(getAdapterPosition());
    }
}
