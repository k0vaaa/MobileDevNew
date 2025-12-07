package ru.mirea.kovalikaa.pocketdictionary.presentation;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import repository.WordRepository;
import ru.mirea.kovalikaa.pocketdictionary.data.network.NetworkApi;
import ru.mirea.kovalikaa.pocketdictionary.data.repository.WordRepositoryImpl;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.SharedPrefWordStorage;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.WordStorage;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.room.AppDatabase;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.room.WordDao;
import usecases.GetWordDefinitionUseCase;
import usecases.SaveFavoriteWordUseCase;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public MainViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        NetworkApi networkApi = new NetworkApi();
        AppDatabase database = Room.databaseBuilder(context, AppDatabase.class, "dictionary-db")
                .allowMainThreadQueries().build();
        WordDao wordDao = database.wordDao();
        WordStorage sharedPrefStorage = new SharedPrefWordStorage(context);

        WordRepository wordRepository = new WordRepositoryImpl(sharedPrefStorage, wordDao, networkApi);

        GetWordDefinitionUseCase getWordDefinitionUseCase = new GetWordDefinitionUseCase(wordRepository);
        SaveFavoriteWordUseCase saveFavoriteWordUseCase = new SaveFavoriteWordUseCase(wordRepository);

        return (T) new MainViewModel(getWordDefinitionUseCase, saveFavoriteWordUseCase);
    }
}