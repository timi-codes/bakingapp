package com.tarrotsystem.codepreneur.bakingrite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.tarrotsystem.codepreneur.bakingrite.fragment.RecipeDetailFragment;
import com.tarrotsystem.codepreneur.bakingrite.model.Recipe;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener{

    public static  String TAG = "com.tarrotsystem.codepreneur.bakingrite.MainActivity";
    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(Recipe clickedItemIndex) {

        final Intent intent = new Intent(this, RecipeDetailFragment.class);
        intent.putExtra(DetailActivity.DETAIL_TAG,clickedItemIndex);
        startActivity(intent);
    }
}
