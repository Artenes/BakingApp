package com.artenesnogueira.bakingapp.views.steps;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.Ingredient;
import com.artenesnogueira.bakingapp.model.ListSectionTitle;
import com.artenesnogueira.bakingapp.model.Recipe;
import com.artenesnogueira.bakingapp.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to display a list of ingredients and steps.
 */
public class IngredientsAndStepsAdapter extends RecyclerView.Adapter {

    public static final int TITLE_VIEW_TYTPE = 1;
    public static final int INGREDIENT_VIEW_TYTPE = 2;
    public static final int STEP_VIEW_TYTPE = 3;

    private List<Object> mListItems = new ArrayList<>(0);
    private final OnStepClicked mOnStepClickedListener;
    private final Context mContext;

    public IngredientsAndStepsAdapter(Context context, OnStepClicked onStepClickedListener) {
        mOnStepClickedListener = onStepClickedListener;
        mContext = context;
    }

    /**
     * Set the current recipe being displayed
     *
     * @param recipe the recipe to display
     */
    public void setData(@NonNull Recipe recipe) {
        mListItems.clear();
        Resources resources = mContext.getResources();

        //create the ingredients section
        ListSectionTitle ingredientsTitle = new ListSectionTitle(resources.getString(R.string.ingredients));
        mListItems.add(ingredientsTitle);
        mListItems.addAll(recipe.getIngredients());

        //create the steps section
        ListSectionTitle stepsTitle = new ListSectionTitle(resources.getString(R.string.steps));
        mListItems.add(stepsTitle);
        mListItems.addAll(recipe.getSteps());

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mListItems.get(position);

        if (item instanceof ListSectionTitle) {
            return TITLE_VIEW_TYTPE;
        }

        if (item instanceof Ingredient) {
            return INGREDIENT_VIEW_TYTPE;
        }

        if (item instanceof Step) {
            return STEP_VIEW_TYTPE;
        }

        throw new IllegalArgumentException("Invalid object found on list: " + item.getClass().getSimpleName());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TITLE_VIEW_TYTPE:
                return new TitleViewHolder(View.inflate(parent.getContext(), R.layout.list_section_title, null));
            case INGREDIENT_VIEW_TYTPE:
                return new IngredientsViewHolder(View.inflate(parent.getContext(), R.layout.ingredient_line, null));
            case STEP_VIEW_TYTPE:
                return new StepsViewHolder(View.inflate(parent.getContext(), R.layout.step_line, null));
        }
        throw new IllegalArgumentException("Invalid view type: " + viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = mListItems.get(position);

        if (holder instanceof TitleViewHolder) {
            ((TitleViewHolder) holder).bind((ListSectionTitle) item);
            return;
        }

        if (holder instanceof IngredientsViewHolder) {
            ((IngredientsViewHolder) holder).bind((Ingredient) item);
            return;
        }

        if (holder instanceof StepsViewHolder) {
            ((StepsViewHolder) holder).bind((Step) item);
        }
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    /**
     * ViewHolder for a title's section
     */
    class TitleViewHolder extends RecyclerView.ViewHolder {

        final TextView description;

        TitleViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.tv_title);
        }

        void bind(ListSectionTitle title) {
            description.setText(title.getTitle());
        }

    }

    /**
     * ViewHolder for an ingredient
     */
    class IngredientsViewHolder extends RecyclerView.ViewHolder {

        final TextView description;
        final TextView quantity;

        IngredientsViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.tv_ingredient);
            quantity = itemView.findViewById(R.id.tv_quantity);
        }

        void bind(Ingredient ingredient) {
            Resources resources = mContext.getResources();
            description.setText(ingredient.getIngredient());
            quantity.setText(resources.getString(R.string.quantity_times_measure, ingredient.getQuantity(), ingredient.getMeasure()));
        }

    }

    /**
     * ViewHolder for a step
     */
    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView description;
        final ImageView thumbnailImageView;

        StepsViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.txv_description);
            thumbnailImageView = itemView.findViewById(R.id.iv_thumbnail);
        }

        void bind(Step step) {
            Resources resources = mContext.getResources();
            String shortDescription = step.getId() == 0 ? step.getShortDescription()
                    : resources.getString(R.string.step_with_number, step.getId(), step.getShortDescription());
            description.setText(shortDescription);
            itemView.setOnClickListener(this);
            if (step.hasThumbnail()) {
                Picasso.get().load(step.getThumbnailURL()).into(thumbnailImageView);
            }
        }

        @Override
        public void onClick(View v) {
            Step step = (Step) mListItems.get(getAdapterPosition());
            mOnStepClickedListener.onStepClicked(step);
        }

    }

    /**
     * Interface for when a step is clicked
     */
    public interface OnStepClicked {
        void onStepClicked(Step step);
    }

}
