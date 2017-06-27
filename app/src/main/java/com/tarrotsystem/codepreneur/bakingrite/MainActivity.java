package com.tarrotsystem.codepreneur.bakingrite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tarrotsystem.codepreneur.bakingrite.model.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(Recipe clickedItemIndex) {

    }
}
