package ru.mirea.kovalikaa.pocketdictionary.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import model.WordDefinition;
import usecases.GetWordDefinitionUseCase;
import usecases.SaveFavoriteWordUseCase;

public class MainViewModel extends ViewModel {

    private final GetWordDefinitionUseCase getWordDefinitionUseCase;
    private final SaveFavoriteWordUseCase saveFavoriteWordUseCase;

    private final MutableLiveData<String> searchTrigger = new MutableLiveData<>();

    private final MediatorLiveData<WordDefinition> _wordDefinition = new MediatorLiveData<>();
    public final LiveData<WordDefinition> wordDefinition = _wordDefinition;

    private final MutableLiveData<String> _toastMessage = new MutableLiveData<>();
    public final LiveData<String> toastMessage = _toastMessage;


    public MainViewModel(GetWordDefinitionUseCase getWordDefinitionUseCase, SaveFavoriteWordUseCase saveFavoriteWordUseCase) {
        this.getWordDefinitionUseCase = getWordDefinitionUseCase;
        this.saveFavoriteWordUseCase = saveFavoriteWordUseCase;

        _wordDefinition.addSource(searchTrigger, word -> {
            if (word != null && !word.isEmpty()) {

                WordDefinition result = getWordDefinitionUseCase.execute(word);
                _wordDefinition.setValue(result);
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
        WordDefinition wordToSave = _wordDefinition.getValue();
        if (wordToSave != null) {
            boolean success = saveFavoriteWordUseCase.execute(wordToSave);
            _toastMessage.setValue("Save successful: " + success);
        } else {
            _toastMessage.setValue("Search for a word first!");
        }
    }
}