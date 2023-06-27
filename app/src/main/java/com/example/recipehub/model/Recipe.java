package com.example.recipehub.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable{
    private int recipeId;
    private int userId;
    private String title;
    private String description;
    private String ingredients;
    private String instructions;

    public Recipe() {
        // Default constructor
    }

    public Recipe(int recipeId, int userId, String title, String description, String ingredients, String instructions) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    // Implement Parcelable methods here

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipeId);
        dest.writeInt(userId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(ingredients);
        dest.writeString(instructions);
    }

    protected Recipe(Parcel in) {
        recipeId = in.readInt();
        userId = in.readInt();
        title = in.readString();
        description = in.readString();
        ingredients = in.readString();
        instructions = in.readString();
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
}
