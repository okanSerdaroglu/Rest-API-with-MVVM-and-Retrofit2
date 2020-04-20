package com.example.recipes.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.recipes.R;
import com.example.recipes.models.Recipe;

public class RecipeActivity extends BaseActivity {

    //UI components
    private AppCompatImageView recipeImage;
    private TextView recipeTitle;
    private TextView recipeRank;
    private LinearLayout recipeIngredientsContainer;
    private ScrollView scrollView;
    private static final String TAG = "RecipeActivity";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recipeImage = findViewById(R.id.recipe_image);
        recipeTitle = findViewById(R.id.recipe_title);
        recipeRank = findViewById(R.id.recipe_social_score);
        recipeIngredientsContainer = findViewById(R.id.ingredients_container);
        scrollView = findViewById(R.id.parent);

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        if (getIntent().hasExtra("recipe")){
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            Log.d(TAG, "getIncomingIntent: " + recipe.getTitle());
        }
    }

}
