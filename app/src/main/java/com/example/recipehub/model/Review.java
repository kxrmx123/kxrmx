package com.example.recipehub.model;

public class Review {
    private int reviewId;
    private int recipeId;
    private int userId;
    private int rating;
    private String comment;

    public Review() {
        // Default constructor
    }

    public Review(int reviewId, int recipeId, int userId, int rating, String comment) {
        this.reviewId = reviewId;
        this.recipeId = recipeId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

