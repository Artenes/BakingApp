package com.artenesnogueira.bakingapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Helper class to index the shared preferences used by the application
 */
public class SharedPreferencesIndex {

    public static SharedPreferences getForIngredients(Context context) {
        return context.getSharedPreferences("INGREDIENTS.sp", Context.MODE_PRIVATE);
    }

}