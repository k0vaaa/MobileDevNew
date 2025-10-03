package ru.mirea.kovalikaa.pocketdictionary.domain.repository;

import ru.mirea.kovalikaa.pocketdictionary.domain.model.WordDefinition;

public interface WordRepository {
    WordDefinition getDefinition(String word);
    boolean saveWordToFavorites(WordDefinition word);
}