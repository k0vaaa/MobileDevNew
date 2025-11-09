package ru.mirea.kovalikaa.lesson9.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.mirea.kovalikaa.lesson9.domain.models.Movie;
import ru.mirea.kovalikaa.lesson9.domain.repository.MovieRepository;
import ru.mirea.kovalikaa.lesson9.domain.usercases.GetFavoriteFilmUseCase;
import ru.mirea.kovalikaa.lesson9.domain.usercases.SaveFilmToFavoriteUseCase;


public class MainViewModel extends ViewModel {

    private final MovieRepository movieRepository;
    private final GetFavoriteFilmUseCase getFavoriteFilmUseCase;
    private final SaveFilmToFavoriteUseCase saveFilmToFavoriteUseCase;

    private final MutableLiveData<String> favoriteMovie = new MutableLiveData<>();

    public LiveData<String> getFavoriteMovie() {
        return favoriteMovie;
    }

    public MainViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.getFavoriteFilmUseCase = new GetFavoriteFilmUseCase(movieRepository);
        this.saveFilmToFavoriteUseCase = new SaveFilmToFavoriteUseCase(movieRepository);
    }

    public void setText(Movie movie) {
        boolean result = saveFilmToFavoriteUseCase.execute(movie);
        if (result) {
            favoriteMovie.setValue("Результат сохранен");
        } else {
            favoriteMovie.setValue("Не удалось сохранить");
        }
    }

    public void getText() {
        Movie movie = getFavoriteFilmUseCase.execute();
        favoriteMovie.setValue(String.format("My favorite movie is %s", movie.getName()));
    }
}