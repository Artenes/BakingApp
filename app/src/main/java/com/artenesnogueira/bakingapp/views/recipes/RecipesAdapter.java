package com.artenesnogueira.bakingapp.views.recipes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to display a list of recipes in card layout.
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipes = new ArrayList<>(0);
    private final OnRecipeClicked mOnRecipeClicked;

    public RecipesAdapter(OnRecipeClicked onRecipeClicked) {
        this.mOnRecipeClicked = onRecipeClicked;
    }

    public void setData(@NonNull List<Recipe> data) {
        mRecipes = data;
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
        holder.bind(mRecipes.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView infoText;
        final Button btnAddToWidget;

        RecipeViewHolder(View itemView) {
            super(itemView);
            infoText = itemView.findViewById(R.id.info_text);
            btnAddToWidget = itemView.findViewById(R.id.btn_add_to_widget);
        }

        void bind(Recipe recipe) {
            infoText.setText(recipe.getName());
            itemView.setOnClickListener(this);
            btnAddToWidget.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Recipe recipe = mRecipes.get(getAdapterPosition());
            switch (view.getId()) {
                case R.id.card_view:
                    mOnRecipeClicked.onRecipeClicked(recipe);
                    break;
                case R.id.btn_add_to_widget:
                    mOnRecipeClicked.onAddToWidgetClicked(recipe);
                    break;
            }

        }

    }

    public interface OnRecipeClicked {
        void onRecipeClicked(Recipe recipe);
        void onAddToWidgetClicked(Recipe recipe);
    }

}
