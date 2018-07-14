package com.artenesnogueira.bakingapp.views.recipes;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

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
        final ImageButton btnAddToWidget;
        final ImageView recipeImageView;
        final TextView servingsTextView;

        RecipeViewHolder(View itemView) {
            super(itemView);
            infoText = itemView.findViewById(R.id.info_text);
            btnAddToWidget = itemView.findViewById(R.id.btn_add_to_widget);
            recipeImageView = itemView.findViewById(R.id.iv_recipe);
            servingsTextView = itemView.findViewById(R.id.tv_servings);
        }

        void bind(Recipe recipe) {
            Resources resources = itemView.getContext().getResources();

            infoText.setText(recipe.getName());
            servingsTextView.setText(resources.getString(R.string.servings, recipe.getServings()));

            if (recipe.hasImage()) {
                Picasso.get().load(recipe.getImage()).into(recipeImageView);
            }

            if (recipe.isOnWidget()) {
                btnAddToWidget.setImageTintList(ColorStateList.valueOf(resources.getColor(R.color.lightRed)));
            } else {
                btnAddToWidget.setImageTintList(ColorStateList.valueOf(resources.getColor(R.color.darkGray)));
            }

            //we set the listener in all of them
            //so the user can click anywhere in the card
            //to open the recipe
            itemView.setOnClickListener(this);
            recipeImageView.setOnClickListener(this);
            infoText.setOnClickListener(this);
            servingsTextView.setOnClickListener(this);
            
            btnAddToWidget.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Recipe recipe = mRecipes.get(getAdapterPosition());
            switch (view.getId()) {
                case R.id.btn_add_to_widget:
                    mOnRecipeClicked.onAddToWidgetClicked(recipe);
                    break;
                default:
                    mOnRecipeClicked.onRecipeClicked(recipe);
                    break;

            }

        }

    }

    public interface OnRecipeClicked {
        void onRecipeClicked(Recipe recipe);

        void onAddToWidgetClicked(Recipe recipe);
    }

}
