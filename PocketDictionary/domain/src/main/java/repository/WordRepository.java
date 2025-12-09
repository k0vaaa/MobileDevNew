package repository;


import java.util.List;

import model.WordDefinition;

public interface WordRepository {
    WordDefinition getDefinition(String word);
    void saveWordToFavorites(String word);
    List<WordDefinition> getFavorites();
    void removeFavorite(WordDefinition word);
}