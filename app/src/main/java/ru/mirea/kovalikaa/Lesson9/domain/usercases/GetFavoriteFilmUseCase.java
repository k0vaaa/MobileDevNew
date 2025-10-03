package ru.mirea.kovalikaa.Lesson9.domain.usercases;

import ru.mirea.kovalikaa.Lesson9.domain.models.Movie;
import ru.mirea.kovalikaa.Lesson9.domain.repository.MovieRepository;

public class GetFavoriteFilmUseCase {
    private MovieRepository movieRepository;

    public GetFavoriteFilmUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie execute() {
        return movieRepository.getMovie();
    }
}