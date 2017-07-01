package com.tarrotsystem.codepreneur.bakingrite.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.tarrotsystem.codepreneur.bakingrite.R;
import com.tarrotsystem.codepreneur.bakingrite.model.Recipe;

import java.util.List;

/**
 * Created by codepreneur on 6/30/17.
 */

public class BakingAppIntentService extends IntentService {

    public BakingAppIntentService() {
        super("BakingAppIntentService");
    }

    public static void startBakingService(Context context, List<Recipe> recipes) {
        Intent intent = new Intent(context, BakingAppIntentService.class);
        intent.putExtra(context.getString(R.string.ingredients),new Gson().toJson(recipes));
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String recipesJson = intent.getStringExtra(getString(R.string.ingredients));
            handleActionUpdateBakingWidgets(recipesJson);
        }
    }

    private void handleActionUpdateBakingWidgets(String recipesJson) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setAction("com.tarrotsystem.codepreneur.bakingrite.widget.ACTION_DATA_UPDATED");
        intent.putExtra(getString(R.string.ingredients),recipesJson);
        sendBroadcast(intent);
    }
}
