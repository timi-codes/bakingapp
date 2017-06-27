package com.tarrotsystem.codepreneur.bakingrite.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codepreneur on 6/26/17.
 */

public class Recipe implements Parcelable {
    private Integer id,servings;
    private String name,image;
    private ArrayList<Ingredient>  ingredient = null;
    private ArrayList<Step> step = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<Ingredient> getIngredientList() {
        return ingredient;
    }

    public void setIngredientList(ArrayList<Ingredient> ingredientList) {
        this.ingredient = ingredientList;
    }

    public ArrayList<Step> getStepList() {
        return step;
    }

    public void setStepList(ArrayList<Step> stepList) {
        this.step = stepList;
    }


    private Recipe(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        if (in.readByte() == 0x01) {
            ingredient = new ArrayList<>();
            in.readList(ingredient, Ingredient.class.getClassLoader());
        } else {
            ingredient = null;
        }
        if (in.readByte() == 0x01) {
            step = new ArrayList<>();
            in.readList(step, Step.class.getClassLoader());
        } else {
            step = null;
        }
        servings = in.readByte() == 0x00 ? null : in.readInt();
        image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(name);
        if (ingredient == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredient);
        }
        if (step == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(step);
        }
        if (servings == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(servings);
        }
        dest.writeString(image);
    }
}
