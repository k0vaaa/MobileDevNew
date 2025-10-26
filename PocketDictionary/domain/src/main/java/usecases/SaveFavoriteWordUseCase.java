package usecases;


import model.WordDefinition;
import repository.WordRepository;

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