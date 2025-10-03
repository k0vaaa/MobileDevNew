package ru.mirea.kovalikaa.pocketdictionary.domain.usecases;

import ru.mirea.kovalikaa.pocketdictionary.domain.model.WordDefinition;
import ru.mirea.kovalikaa.pocketdictionary.domain.repository.WordRepository;

public class GetWordDefinitionUseCase {
    private final WordRepository wordRepository;

    public GetWordDefinitionUseCase(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public WordDefinition execute(String word) {
        return wordRepository.getDefinition(word);
    }
}