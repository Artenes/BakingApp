package com.artenesnogueira.bakingapp.model;

import android.support.annotation.NonNull;

/**
 * A title of a section in a list
 */
public class ListSectionTitle {

    private final String title;

    public ListSectionTitle(@NonNull String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}