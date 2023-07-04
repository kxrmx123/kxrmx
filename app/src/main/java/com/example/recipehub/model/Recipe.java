package com.example.recipehub.model;

public class Recipe {
    private int recipe_id; // Updated field name
    private int user_id; // Updated field name
    private String title;
    private String description;
    private String ingredients;
    private String instructions;

    public Recipe() {
        // Default constructor
    }

    public Recipe(int user_id, String title, String description, String ingredients, String instructions) {
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public int getRecipe_id() { // Updated getter method name
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) { // Updated setter method name
        this.recipe_id = recipe_id;
    }

    public int getUser_id() { // Updated getter method name
        return user_id;
    }

    public void setUser_id(int user_id) { // Updated setter method name
        this.user_id = user_id;
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

    @Override
    public String toString() {
        return "Recipe{" +
                "recipe_id=" + recipe_id +
                ", user_id=" + user_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}
