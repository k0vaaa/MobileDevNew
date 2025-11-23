package ru.mirea.kovalikaa.pocketdictionary.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import model.WordDefinition;
import usecases.GetFavoritesUseCase;
import usecases.RemoveFavoriteUseCase;

public class FavoritesViewModel extends ViewModel {

    private final GetFavoritesUseCase getFavoritesUseCase;
    private final RemoveFavoriteUseCase removeFavoriteUseCase;

    private final MutableLiveData<List<WordDefinition>> _favoritesList = new MutableLiveData<>();
    public final LiveData<List<WordDefinition>> favoritesList = _favoritesList;

    public FavoritesViewModel(GetFavoritesUseCase getFavoritesUseCase, RemoveFavoriteUseCase removeFavoriteUseCase) {
        this.getFavoritesUseCase = getFavoritesUseCase;
        this.removeFavoriteUseCase = removeFavoriteUseCase;
    }

    public void loadFavorites() {
        List<WordDefinition> favorites = getFavoritesUseCase.execute();
        _favoritesList.setValue(favorites);
    }

    public void deleteWord(WordDefinition word) {
        removeFavoriteUseCase.execute(word);
        loadFavorites();
    }
}