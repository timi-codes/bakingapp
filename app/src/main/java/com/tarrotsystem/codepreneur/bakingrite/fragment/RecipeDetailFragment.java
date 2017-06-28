package com.tarrotsystem.codepreneur.bakingrite.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tarrotsystem.codepreneur.bakingrite.R;
import com.tarrotsystem.codepreneur.bakingrite.model.Recipe;

import java.util.ArrayList;

/**
 * Created by codepreneur on 6/28/17.
 */

public class RecipeDetailFragment extends Fragment {
    ArrayList<Recipe> recipes;


    public RecipeDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail_fragment, container, false);
        return rootView;
    }
}
