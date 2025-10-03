package ru.mirea.kovalikaa.Lesson9.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import ru.mirea.kovalikaa.Lesson9.domain.models.Movie;
import ru.mirea.kovalikaa.Lesson9.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {
    private static final String PREFERENCES_NAME = "movie_settings";
    private static final String KEY_FAVORITE_MOVIE_NAME = "favorite_movie_name";
    private static final String DEFAULT_MOVIE_NAME = "Нет сохраненного фильма";
    private final SharedPreferences sharedPreferences;

    public MovieRepositoryImpl(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean saveMovie(Movie movie) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FAVORITE_MOVIE_NAME, movie.getName());
        editor.apply();
        return true;
    }

    @Override
    public Movie getMovie() {
        String movieName = sharedPreferences.getString(KEY_FAVORITE_MOVIE_NAME, DEFAULT_MOVIE_NAME);
        return new Movie(1, movieName);
    }
}
