package com.example.recipehub.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipehub.R;
import com.example.recipehub.model.Review;
import com.example.recipehub.model.User;
import com.example.recipehub.remote.ApiUtils;
import com.example.recipehub.remote.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviews;
    private UserService userService;
    private String apiKey;

    public ReviewAdapter(List<Review> reviews, UserService userService, String apiKey) {
        this.reviews = reviews;
        this.userService = userService;
        this.apiKey = apiKey;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        //Log.d("onbindviewreview", review.toString());
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewUsername;
        private TextView textViewRating;
        private TextView textViewComment;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewComment = itemView.findViewById(R.id.textViewComment);
        }

        public void bind(Review review) {
            String userId = String.valueOf(review.getUserId());
            //Log.d("bindapi", apiKey);

            // Fetch the username based on the user ID
            fetchUsername(apiKey, userId);

            String ratingText = "Rating: " + review.getRating();
            textViewRating.setText(ratingText);
            textViewComment.setText(review.getComment());
        }

        private void fetchUsername(String apiKey, String userId) {
            //Log.d("fetchapi", apiKey);
            //Log.d("fetchuserid", userId);

            Call<User> call = userService.getUser(apiKey, userId);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User user = response.body();
                        if (user != null) {
                            String username = user.getUsername();
                            textViewUsername.setText(username);
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    // Handle failure
                }
            });
        }
    }
}

