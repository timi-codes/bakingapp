package com.tarrotsystem.codepreneur.bakingrite.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tarrotsystem.codepreneur.bakingrite.R;
import com.tarrotsystem.codepreneur.bakingrite.model.Step;
import com.tarrotsystem.codepreneur.bakingrite.utils.RecipeUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by codepreneur on 6/29/17.
 */

public class RecipeStepAdapter  extends RecyclerView.Adapter<RecipeStepAdapter.RecyclerViewHolder> {
        private ArrayList<Step> mSteps;
        private Context mContext;
        private RecipeStepAdapter.RecipeClickListener mListener;

        public RecipeStepAdapter(RecipeStepAdapter.RecipeClickListener recipeStepClickListener) {
            this.mListener = recipeStepClickListener;
        }

        public interface RecipeClickListener {
            void onClick(ArrayList<Step> allSteps,int selectedItem);
        }

        public void setStepsRecipeData(ArrayList<Step> steps, Context context) {
            this.mSteps = steps;
            this.mContext = context;
            notifyDataSetChanged();
        }

        @Override
        public RecipeStepAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            int layoutIdForListItem = R.layout.recipe_step_item;
            View view = LayoutInflater.from(mContext).inflate(layoutIdForListItem, parent, false);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecipeStepAdapter.RecyclerViewHolder holder, int position) {
            holder.stepShortDesc.setText(mSteps.get(position).getShortDescription());

           /* try {
                Bitmap bitmap = RecipeUtils.retriveVideoFrameFromVideo(mSteps.get(position).getVideoURL());
                holder.stepImage.setImageBitmap(bitmap);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
*/

        }

        @Override
        public int getItemCount() {
            return mSteps != null ? mSteps.size() : 0;
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.step_desc)
            TextView stepShortDesc;

            @BindView(R.id.step_image)
            ImageView stepImage;


            public RecyclerViewHolder(final View itemView) {
                super(itemView);

                ButterKnife.bind(this, itemView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onClick(mSteps,getAdapterPosition());
                    }
                });
            }
        }

}
