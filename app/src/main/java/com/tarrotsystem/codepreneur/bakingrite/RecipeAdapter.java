package com.tarrotsystem.codepreneur.bakingrite;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tarrotsystem.codepreneur.bakingrite.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by codepreneur on 6/26/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecyclerViewHolder> {
    private ArrayList<Recipe> mRecipes;
    Context mContext;
    private RecipeClickListener mListener;

    public RecipeAdapter(RecipeClickListener recipeClickListener){
        this.mListener = recipeClickListener;
    }

    public interface RecipeClickListener{
        void onClick(Recipe clickedItemIndex);
    }

    public void setRecipeData(ArrayList<Recipe> recipes, Context context) {
        this.mRecipes = recipes;
        this.mContext=context;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.recipe_card_view;
        View view =  LayoutInflater.from(mContext).inflate(layoutIdForListItem,parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.txtRecipeName.setText(mRecipes.get(position).getName());
        String imageUrl = mRecipes.get(position).getImage();
        if (imageUrl!=null){
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            Picasso.with(mContext).load(builtUri).into(holder.txtRecipeImage);
        }

        // TODO : set ingredient textview by looping through mRecipe.get(position).getIngredient(),
    }

    @Override
    public int getItemCount() {
        return mRecipes!=null   ?  mRecipes.size() : 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.recipe_name) TextView txtRecipeName;
        @BindView(R.id.recipe_image) ImageView txtRecipeImage;
        @BindView(R.id.recipe_ingredient) TextView txtRecipeIngredient;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(mRecipes.get(getLayoutPosition()));
                }
            });
        }
    }
}
