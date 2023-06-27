package com.example.recipehub.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipehub.R;
import com.example.recipehub.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;
    private RecipeClickListener clickListener;

    public RecipeAdapter(RecipeClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
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

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewRecipeTitle;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRecipeTitle = itemView.findViewById(R.id.textViewRecipeTitle);
            itemView.setOnClickListener(this);
        }

        public void bind(Recipe recipe) {
            textViewRecipeTitle.setText(recipe.getTitle());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Recipe recipe = recipes.get(position);
                clickListener.onRecipeClick(recipe);
            }
        }
    }

    public interface RecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }
}
