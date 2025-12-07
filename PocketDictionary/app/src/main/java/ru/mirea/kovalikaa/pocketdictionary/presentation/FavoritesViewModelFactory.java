package ru.mirea.kovalikaa.pocketdictionary.presentation;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import repository.WordRepository;
import ru.mirea.kovalikaa.pocketdictionary.data.repository.WordRepositoryImpl;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.room.AppDatabase;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.room.WordDao;
import usecases.GetFavoritesUseCase;
import usecases.RemoveFavoriteUseCase;


public class FavoritesViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public FavoritesViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        AppDatabase database = Room.databaseBuilder(context, AppDatabase.class, "dictionary-db")
                .allowMainThreadQueries().build();
        WordDao wordDao = database.wordDao();
        WordRepository wordRepository = new WordRepositoryImpl(null, wordDao, null);

        GetFavoritesUseCase getFavoritesUseCase = new GetFavoritesUseCase(wordRepository);
        RemoveFavoriteUseCase removeFavoriteUseCase = new RemoveFavoriteUseCase(wordRepository);

        return (T) new FavoritesViewModel(getFavoritesUseCase, removeFavoriteUseCase);
    }
}

