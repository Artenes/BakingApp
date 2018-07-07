package com.artenesnogueira.bakingapp.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to display a list of recipes in card layout.
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private List<Recipe> recipes = new ArrayList<>(0);

    public void setData(@NonNull List<Recipe> data) {
        recipes = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.recipe_card, null);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        final TextView infoText;

        RecipeViewHolder(View itemView) {
            super(itemView);
            infoText = itemView.findViewById(R.id.info_text);
        }

        void bind(Recipe recipe) {
            infoText.setText(recipe.getName());
        }

    }

}