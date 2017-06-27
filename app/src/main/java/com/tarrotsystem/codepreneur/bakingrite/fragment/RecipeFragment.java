package com.tarrotsystem.codepreneur.bakingrite.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tarrotsystem.codepreneur.bakingrite.MainActivity;
import com.tarrotsystem.codepreneur.bakingrite.R;
import com.tarrotsystem.codepreneur.bakingrite.RecipeAdapter;

import butterknife.BindView;

/**
 * Created by codepreneur on 6/26/17.
 */

public class RecipeFragment extends Fragment {
    @BindView(R.id.recipe_recycler)
    RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;

    public RecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_recyclerview,container,false);
        recipeAdapter = new RecipeAdapter((MainActivity)getActivity());
        recyclerView.setAdapter(recipeAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
