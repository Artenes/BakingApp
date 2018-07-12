package com.artenesnogueira.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViewsService;

import com.artenesnogueira.bakingapp.provider.IngredientsRepository;
import com.artenesnogueira.bakingapp.utilities.JsonParser;

/**
 * Service to create the factory to generate the ingredient lines for the widget
 */
public class IngredientsListViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences("INGREDIENTS.sp", Context.MODE_PRIVATE);

        IngredientsRepository repository =
                new IngredientsRepository(sharedPreferences, new JsonParser());

        return new IngredientsListViewFactory(getApplicationContext(), repository);
    }

}
