package com.artenesnogueira.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.widget.RemoteViews;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.ResumedRecipe;
import com.artenesnogueira.bakingapp.provider.IngredientsRepository;
import com.artenesnogueira.bakingapp.utilities.JsonParser;
import com.artenesnogueira.bakingapp.utilities.SharedPreferencesIndex;
import com.artenesnogueira.bakingapp.views.recipes.RecipesActivity;

import java.io.IOException;

/**
 * Provider for widget to display the ingredients of a recipe
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    private IngredientsRepository mRepository;

    /**
     * Update a widget
     *
     * @param context          the current context
     * @param appWidgetManager the widget manager instance
     * @param appWidgetId      the id of the widget to update
     * @param repository       the repository from where the recipes will be recovered
     */
    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, IngredientsRepository repository) {

        try {

            Resources resources = context.getResources();
            ResumedRecipe recipe = repository.get();
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

            views.setTextViewText(R.id.tv_recipe, recipe.hasName() ? recipe.getName() : resources.getString(R.string.no_recipe));

            Intent intent = new Intent(context, IngredientsListViewService.class);
            views.setRemoteAdapter(R.id.lv_ingredients, intent);

            Intent appIntent = new Intent(context, RecipesActivity.class);
            PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.btn_choose, appPendingIntent);

            views.setEmptyView(R.id.lv_ingredients, R.id.empty_view);

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_ingredients);

            appWidgetManager.updateAppWidget(appWidgetId, views);

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        if (mRepository == null) {
            SharedPreferences sharedPreferences = SharedPreferencesIndex.getForIngredients(context);
            mRepository = new IngredientsRepository(sharedPreferences, new JsonParser());
        }

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, mRepository);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
    
}