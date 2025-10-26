package usecases;


import model.WordDefinition;
import repository.WordRepository;

public class GetWordDefinitionUseCase {
    private final WordRepository wordRepository;

    public GetWordDefinitionUseCase(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public WordDefinition execute(String word) {
        return wordRepository.getDefinition(word);
    }
}