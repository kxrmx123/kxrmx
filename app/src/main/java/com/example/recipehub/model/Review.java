package com.example.recipehub.model;

public class Review {
    private int review_id;
    private int recipe_id;
    private int user_id;
    private int rating;
    private String comment;

    public Review() {
        // Default constructor
    }

    public Review(int recipe_id, int user_id, int rating, String comment) {
        this.recipe_id = recipe_id;
        this.user_id = user_id;
        this.rating = rating;
        this.comment = comment;
    }

    public int getReviewId() {
        return review_id;
    }

    public void setReviewId(int review_id) {
        this.review_id = review_id;
    }

    public int getRecipeId() {
        return recipe_id;
    }

    public void setRecipeId(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
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

    @Override
    public String toString() {
        return "Review{" +
                "review_id=" + review_id +
                ", recipe_id=" + recipe_id +
                ", user_id=" + user_id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}
