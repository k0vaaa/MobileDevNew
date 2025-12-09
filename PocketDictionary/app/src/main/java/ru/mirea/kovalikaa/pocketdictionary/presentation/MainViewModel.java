package ru.mirea.kovalikaa.pocketdictionary.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.WordDefinition;
import usecases.GetWordDefinitionUseCase;
import usecases.LogoutUseCase;
import usecases.SaveFavoriteWordUseCase;

public class MainViewModel extends ViewModel {

    private final GetWordDefinitionUseCase getWordDefinitionUseCase;
    private final LogoutUseCase logoutUseCase;
    private final SaveFavoriteWordUseCase saveFavoriteWordUseCase;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final MutableLiveData<String> searchTrigger = new MutableLiveData<>();
    private final MediatorLiveData<WordDefinition> _wordDefinition = new MediatorLiveData<>();
    public final LiveData<WordDefinition> wordDefinition = _wordDefinition;
    private final MutableLiveData<String> _toastMessage = new MutableLiveData<>();
    public final LiveData<String> toastMessage = _toastMessage;

    public MainViewModel(GetWordDefinitionUseCase getWordDefinitionUseCase, SaveFavoriteWordUseCase saveFavoriteWordUseCase, LogoutUseCase logoutUseCase) {
        this.getWordDefinitionUseCase = getWordDefinitionUseCase;
        this.saveFavoriteWordUseCase = saveFavoriteWordUseCase;
        this.logoutUseCase = logoutUseCase;
        _wordDefinition.addSource(searchTrigger, word -> {
            if (word != null && !word.isEmpty()) {
                executorService.execute(() -> {
                    WordDefinition result = getWordDefinitionUseCase.execute(word);
                    _wordDefinition.postValue(result);
                });
            }
        });
    }

    public void searchWord(String word) {
        if (word == null || word.isEmpty()) {
            _toastMessage.setValue("Please enter a word");
            return;
        }
        searchTrigger.setValue(word);
    }

    public void saveCurrentWord() {
        executorService.execute(() -> {
            WordDefinition wordToSave = _wordDefinition.getValue();
            if (wordToSave != null) {
                saveFavoriteWordUseCase.execute(wordToSave.getWord());
                _toastMessage.postValue("Saved to favorites!");
            } else {
                _toastMessage.postValue("Search for a word first!");
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }

    public void logout() {
        logoutUseCase.execute();
    }
}