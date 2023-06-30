package com.example.recipehub.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipehub.R;
import com.example.recipehub.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;
    private RecipeClickListener clickListener;
    private RecipeLongClickListener longClickListener;

    public RecipeAdapter(RecipeClickListener clickListener, RecipeLongClickListener longClickListener) {
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public void setRecipeLongClickListener(RecipeLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView textViewRecipeTitle;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRecipeTitle = itemView.findViewById(R.id.textViewRecipeTitle);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bind(Recipe recipe) {
            textViewRecipeTitle.setText(recipe.getTitle());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            //Log.d("postiot", String.valueOf(position));
            if (position != RecyclerView.NO_POSITION) {
                Recipe recipe = recipes.get(position);
                clickListener.onRecipeClick(recipe);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Recipe recipe = recipes.get(position);
                longClickListener.onRecipeLongClick(recipe);
            }
            return true;
        }
    }

    public interface RecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public interface RecipeLongClickListener {
        void onRecipeLongClick(Recipe recipe);
    }
}
