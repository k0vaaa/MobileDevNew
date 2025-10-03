package ru.mirea.kovalikaa.pocketdictionary.data.repository;

import ru.mirea.kovalikaa.pocketdictionary.domain.model.WordDefinition;
import ru.mirea.kovalikaa.pocketdictionary.domain.repository.WordRepository;

public class WordRepositoryImpl implements WordRepository {
    @Override
    public WordDefinition getDefinition(String word) {
        return new WordDefinition(word, "This is a test definition from the repository's test data.");
    }

    @Override
    public boolean saveWordToFavorites(WordDefinition word) {
        System.out.println("TEST DATA: Saving word '" + word.getWord() + "' was successful.");
        return true;
    }
}