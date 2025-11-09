package ru.mirea.kovalikaa.lesson9.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

import ru.mirea.kovalikaa.lesson9.domain.models.Movie;

public class SharedPrefMovieStorage implements MovieStorage {

    private static final String SHARED_PREFS_NAME = "movie_prefs";
    private static final String KEY_MOVIE_NAME = "movie_name";
    private static final String DEFAULT_MOVIE_NAME = "No movie saved";

    private final SharedPreferences sharedPreferences;

    public SharedPrefMovieStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean save(Movie movie) {
        sharedPreferences.edit().putString(KEY_MOVIE_NAME, movie.getName()).apply();
        return true;
    }

    @Override
    public Movie get() {
        String movieName = sharedPreferences.getString(KEY_MOVIE_NAME, DEFAULT_MOVIE_NAME);
        return new Movie(1, movieName);
    }
}