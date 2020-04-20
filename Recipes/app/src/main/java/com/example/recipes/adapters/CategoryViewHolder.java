package com.example.recipes.adapters;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.recipes.R;
import com.example.recipes.models.Recipe;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder
        extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    private CircleImageView categoryImage;
    private OnRecipeListener listener;
    private TextView categoryTitle;

    CategoryViewHolder(@NonNull View itemView,OnRecipeListener onRecipeListener) {
        super(itemView);
        this.listener = onRecipeListener;
        categoryImage = itemView.findViewById(R.id.category_image);
        categoryTitle = itemView.findViewById(R.id.category_title);
    }
    @Override
    public void onClick(View v) {

    }

    void onBind(Recipe recipe) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Uri uri = Uri.parse("android.resource://com.example.recipes/drawable/" + recipe.getImage_url());
        Glide.with(categoryImage.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(uri)
                .into(categoryImage);

        categoryTitle.setText(recipe.getTitle());
    }
}
