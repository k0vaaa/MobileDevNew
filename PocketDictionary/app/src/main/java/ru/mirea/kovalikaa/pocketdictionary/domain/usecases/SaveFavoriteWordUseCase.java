package ru.mirea.kovalikaa.pocketdictionary.domain.usecases;

import ru.mirea.kovalikaa.pocketdictionary.domain.model.WordDefinition;
import ru.mirea.kovalikaa.pocketdictionary.domain.repository.WordRepository;

public class SaveFavoriteWordUseCase {
    private final WordRepository wordRepository;

    public SaveFavoriteWordUseCase(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public boolean execute(WordDefinition word) {
        if (word.getWord() == null || word.getWord().isEmpty()) {
            return false;
        }
        return wordRepository.saveWordToFavorites(word);
    }
}