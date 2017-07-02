package com.tarrotsystem.codepreneur.bakingrite.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.tarrotsystem.codepreneur.bakingrite.DetailActivity;
import com.tarrotsystem.codepreneur.bakingrite.R;
import com.tarrotsystem.codepreneur.bakingrite.model.Ingredient;
import com.tarrotsystem.codepreneur.bakingrite.model.Recipe;
import com.tarrotsystem.codepreneur.bakingrite.model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tarrotsystem.codepreneur.bakingrite.DetailActivity.SELECTED_INDEX;
import static com.tarrotsystem.codepreneur.bakingrite.DetailActivity.SELECTED_RECIPES;
import static com.tarrotsystem.codepreneur.bakingrite.DetailActivity.SELECTED_STEPS;

/**
 * Created by codepreneur on 6/29/17.
 */

public class RecipeStepDetailFragment extends Fragment {
    private SimpleExoPlayer player;
    private BandwidthMeter bandwidthMeter;
    private ArrayList<Step> steps = new ArrayList<>();
    private int selectedIndex;
    private Handler handler;
    ArrayList<Recipe> recipes;

    @Nullable
    @BindView(R.id.description)
    TextView description;


    @BindView(R.id.mPlayerView)
    SimpleExoPlayerView mPlayerView;

    @Nullable
    @BindView(R.id.collapsingtbl)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Nullable
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @BindView(R.id.fragment_container)
    NestedScrollView nestedScrollView;

    @Nullable
    @BindView(R.id.btnIngredients)
    FloatingActionButton btnIngredient;




    private String recipeName;

    private RecipeClickListener mListener;

    public interface RecipeClickListener {
        void onClick(ArrayList<Step> allSteps,int selectedItem);
    }


    public RecipeStepDetailFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        handler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();
        recipes = new ArrayList<>();
        mListener =(DetailActivity)getActivity();

        if (savedInstanceState!=null){
            steps = savedInstanceState.getParcelableArrayList(SELECTED_STEPS);
            selectedIndex = savedInstanceState.getInt(SELECTED_INDEX);
            recipeName = savedInstanceState.getString("Title");
            recipes = savedInstanceState.getParcelableArrayList(SELECTED_RECIPES);
        }else{
            recipes = getArguments().getParcelableArrayList(SELECTED_RECIPES);
            steps = getArguments().getParcelableArrayList(SELECTED_STEPS);
            if (steps!=null){
                selectedIndex = getArguments().getInt(SELECTED_INDEX);
                recipeName=getArguments().getString("Title");
            }else {
                steps = recipes.get(0).getStepList();
            }
        }

        View rootView = inflater.inflate(R.layout.step_player_fragment, container, false);
        ButterKnife.bind(this,rootView);


        if (toolbar!=null){
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(recipeName);
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            collapsingToolbarLayout.setTitle(recipeName);
            collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));



            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_previous:
                            if (steps.get(selectedIndex).getId() > 0) {
                                if (player!=null){
                                    player.stop();
                                }
                                mListener.onClick(steps,steps.get(selectedIndex).getId() - 1);
                            }
                            else {
                                Toast.makeText(getActivity(),"You already are in the First step of the recipe", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case R.id.action_next:
                            int lastIndex = steps.size()-1;
                            if (steps.get(selectedIndex).getId() < steps.get(lastIndex).getId()) {
                                if (player!=null){
                                    player.stop();
                                }
                                mListener.onClick(steps,steps.get(selectedIndex).getId() + 1);
                            }
                            else {
                                Toast.makeText(getContext(),"You already are in the Last step of the recipe", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                    return true;
                }
            });
        }

        if(btnIngredient!=null){
            btnIngredient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setupDialog();
                }
            });
        }

        description.setText(steps.get(selectedIndex).getDescription());
        mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        String videoUrl = steps.get(selectedIndex).getVideoURL();

        if (videoUrl.isEmpty()){
            player = null;
            mPlayerView.setForeground(ContextCompat.getDrawable(getContext(), R.drawable.ic_video_error));
        }else {
            initializePlayer(Uri.parse(videoUrl));
            if (isLandscapeMode(getContext())){
               description.setVisibility(View.GONE);

                if (bottomNavigationView!=null)
                    bottomNavigationView.setVisibility(View.GONE);

                if (collapsingToolbarLayout!=null)
                    collapsingToolbarLayout.setTitle(" ");

                if (nestedScrollView!=null)
                    nestedScrollView.setVisibility(View.GONE);
            }

        }

        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (player == null) {
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(handler, videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(player);

            String userAgent = Util.getUserAgent(getContext(), "Bake Rite");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }

    private void setupDialog(){
        LayoutInflater inflater = LayoutInflater.from(getContext());;
        View dialogueLayout = inflater.inflate(R.layout.custom_dialog, null);
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setView(dialogueLayout);
        builder.setTitle(Html.fromHtml("<font color='#399f8c'>You Need</font>"));
        builder.setIcon(R.drawable.ic_ingredients);
        builder.setPositiveButton("OK",null).create()
                .show();

        final TextView ingredient = (TextView)dialogueLayout.findViewById(R.id.ingredient);

        ArrayList<Ingredient> ingredients = recipes.get(0).getIngredientList();

        for(Ingredient a: ingredients){
            ingredient.append("\u2022  "+ a.getQuantity().toString());
            ingredient.append(a.getMeasure());
            ingredient.append(" of "+a.getIngredient()+"\n\n");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player!=null) {
            player.stop();
            player.release();
            player=null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }
    public boolean isLandscapeMode( Context context ) {
        return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SELECTED_STEPS,steps);
        outState.putParcelableArrayList(SELECTED_RECIPES,recipes);
        outState.putInt(SELECTED_INDEX,selectedIndex);
        outState.putString("Title",recipeName);

    }
}
