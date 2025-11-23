package usecases;

import java.util.List;

import model.WordDefinition;
import repository.WordRepository;

public class GetFavoritesUseCase {
    private final WordRepository repository;
    public GetFavoritesUseCase(WordRepository repository) { this.repository = repository; }
    public List<WordDefinition> execute() { return repository.getFavorites(); }
}