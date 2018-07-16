package com.artenesnogueira.bakingapp.widget;

import android.content.Context;
import android.content.res.Resources;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.Ingredient;
import com.artenesnogueira.bakingapp.model.ResumedRecipe;
import com.artenesnogueira.bakingapp.provider.IngredientsRepository;

import java.io.IOException;

/**
 * Factory to create the views to display in the widget for each ingredient
 */
public class IngredientsListViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private final IngredientsRepository mRepository;
    private ResumedRecipe mResumedRecipe;

    public IngredientsListViewFactory(Context context, IngredientsRepository repository) {
        mContext = context;
        mRepository = repository;
    }

    @Override
    public void onCreate() {
        updateIngredients();
    }

    @Override
    public void onDataSetChanged() {
        updateIngredients();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return mResumedRecipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Resources resources = mContext.getResources();

        Ingredient ingredient = mResumedRecipe.getIngredients().get(position);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_line);

        views.setTextViewText(R.id.tv_ingredient, ingredient.getIngredient());
        views.setTextViewText(R.id.tv_quantity, resources.getString(R.string.quantity_times_measure, ingredient.getQuantity(), ingredient.getMeasure()));

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void updateIngredients() {
        try {
            mResumedRecipe = mRepository.get();
        } catch (IOException exception) {
            mResumedRecipe = new ResumedRecipe();
            exception.printStackTrace();
        }
    }

}
