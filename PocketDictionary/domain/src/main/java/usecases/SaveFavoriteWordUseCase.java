package usecases;


import model.WordDefinition;
import repository.WordRepository;

public class SaveFavoriteWordUseCase {
    private final WordRepository wordRepository;

    public SaveFavoriteWordUseCase(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public void execute(String word) {
        if (word != null && !word.isEmpty()) {
            wordRepository.saveWordToFavorites(word);
        }
    }
}