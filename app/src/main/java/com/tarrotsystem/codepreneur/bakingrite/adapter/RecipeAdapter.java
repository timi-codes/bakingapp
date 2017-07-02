package com.tarrotsystem.codepreneur.bakingrite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tarrotsystem.codepreneur.bakingrite.R;
import com.tarrotsystem.codepreneur.bakingrite.model.Ingredient;
import com.tarrotsystem.codepreneur.bakingrite.model.Recipe;
import com.tarrotsystem.codepreneur.bakingrite.utils.RecipeUtils;
import com.tarrotsystem.codepreneur.bakingrite.widget.BakingAppIntentService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by codepreneur on 6/26/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecyclerViewHolder> {
    private ArrayList<Recipe> mRecipes;
    private Context mContext;
    private RecipeClickListener mListener;

    public RecipeAdapter(RecipeClickListener recipeClickListener) {
        this.mListener = recipeClickListener;
    }

    public interface RecipeClickListener {
        void onClick(Recipe clickedItemIndex);
    }

    public void setRecipeData(ArrayList<Recipe> recipes, Context context) {
        this.mRecipes = recipes;
        this.mContext = context;
        notifyDataSetChanged();
        BakingAppIntentService.startBakingService(context,mRecipes);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.recipe_card_view;
        View view = LayoutInflater.from(mContext).inflate(layoutIdForListItem, parent, false);
        return new RecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.txtRecipeName.setText(mRecipes.get(position).getName());

        if (mRecipes.get(position).getImage().isEmpty()){
            Picasso.with(mContext).load(RecipeUtils.getImageById(mRecipes.get(position).getId())).into(holder.txtRecipeImage);
        }else{
            Picasso.with(mContext).load(mRecipes.get(position).getImage()).into(holder.txtRecipeImage);
        }



        StringBuilder sb = new StringBuilder(5);
        ArrayList<Ingredient> ingredients = mRecipes.get(position).getIngredientList();
        for (int i = 0; i < ingredients.size(); i++) {
            sb.append(ingredients.get(i).getIngredient());
            sb.append(", ");
        }
        holder.txtRecipeIngredient.setText(sb);

        int numberOfSteps = mRecipes.get(position).getStepList().size();
        holder.textRecipeNmIngredient.setText(numberOfSteps + " ");

    }

    @Override
    public int getItemCount() {
        return mRecipes != null ? mRecipes.size() : 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_name)
        TextView txtRecipeName;
        @BindView(R.id.recipe_image)
        ImageView txtRecipeImage;
        @BindView(R.id.recipe_ingredient)
        TextView txtRecipeIngredient;
        @BindView(R.id.recipe_nm_steps)
        TextView textRecipeNmIngredient;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(mRecipes.get(getLayoutPosition()));
                }
            });
        }
    }

}
