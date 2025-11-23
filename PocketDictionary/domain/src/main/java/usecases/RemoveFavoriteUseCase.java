package usecases;

import model.WordDefinition;
import repository.WordRepository;

public class RemoveFavoriteUseCase {
    private final WordRepository repository;
    public RemoveFavoriteUseCase(WordRepository repository) { this.repository = repository; }
    public void execute(WordDefinition word) { repository.removeFavorite(word); }
}