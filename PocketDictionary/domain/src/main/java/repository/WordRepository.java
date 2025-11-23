package repository;


import model.WordDefinition;
import java.util.List;

public interface WordRepository {
    WordDefinition getDefinition(String word);
    boolean saveWordToFavorites(WordDefinition word);
    List<WordDefinition> getFavorites();
    void removeFavorite(WordDefinition word);
}