package com.tarrotsystem.codepreneur.bakingrite.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;

import com.tarrotsystem.codepreneur.bakingrite.R;

import java.util.HashMap;

/**
 * Created by codepreneur on 6/29/17.
 */

public class RecipeUtils {
    public static int getImageById(int id) {
        switch (id) {
            case 1:
                return R.drawable.nutellapie;
            case 2:
                return R.drawable.brownies;
            case 3:
                return R.drawable.yellowcake;
            case 4:
                return R.drawable.cheesecake;
            default:
                return R.drawable.recipe_placeholder;
        }
    }

    public static String convertPositiontoWord(int id) {
        switch (id) {
            case 0:
                return "One";
            case 1:
                return "Two";
            case 2:
                return "Three";
            case 3:
                return "Four";
            case 4:
                return "Five";
            case 5:
                return "Six";
            case 6:
                return "Seven";
            case 7:
                return "Eight";
            case 8:
                return "Nine";
            case 9:
                return "Ten";
            case 10:
                return "Eleven";
            case 11:
                return "Twelve";
            case 12:
                return "Thirteen";
            case 13:
                return "Fourteen";
            case 14:
                return "Fifteen";
            default:
                return null;
        }
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }
}
