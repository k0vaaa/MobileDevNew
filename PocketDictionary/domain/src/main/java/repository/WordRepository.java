package repository;


import model.WordDefinition;

public interface WordRepository {
    WordDefinition getDefinition(String word);
    boolean saveWordToFavorites(WordDefinition word);
}