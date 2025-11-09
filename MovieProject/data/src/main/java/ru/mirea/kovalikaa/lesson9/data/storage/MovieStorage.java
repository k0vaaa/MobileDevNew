package ru.mirea.kovalikaa.lesson9.data.storage;


import ru.mirea.kovalikaa.lesson9.domain.models.Movie;

public interface MovieStorage {
    boolean save(Movie movie);
    Movie get();
}