package ru.mirea.kovalikaa.pocketdictionary.presentation;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.google.firebase.auth.FirebaseAuth;

import repository.AuthRepository;
import repository.WordRepository;
import ru.mirea.kovalikaa.pocketdictionary.data.network.NetworkApi;
import ru.mirea.kovalikaa.pocketdictionary.data.repository.AuthRepositoryImpl;
import ru.mirea.kovalikaa.pocketdictionary.data.repository.WordRepositoryImpl;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.SharedPrefWordStorage;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.WordStorage;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.room.AppDatabase;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.room.WordDao;
import usecases.GetWordDefinitionUseCase;
import usecases.LogoutUseCase;
import usecases.SaveFavoriteWordUseCase;
import usecases.GetUserEmailUseCase;
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
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();
        WordDao wordDao = database.wordDao();
        WordStorage sharedPrefStorage = new SharedPrefWordStorage(context);

        WordRepository wordRepository = new WordRepositoryImpl(sharedPrefStorage, wordDao, networkApi);
        GetWordDefinitionUseCase getWordDefinitionUseCase = new GetWordDefinitionUseCase(wordRepository);
        SaveFavoriteWordUseCase saveFavoriteWordUseCase = new SaveFavoriteWordUseCase(wordRepository);

        AuthRepository authRepository = new AuthRepositoryImpl(FirebaseAuth.getInstance());
        LogoutUseCase logoutUseCase = new LogoutUseCase(authRepository);

        GetUserEmailUseCase getUserEmailUseCase = new GetUserEmailUseCase(authRepository);
        return (T) new MainViewModel(getWordDefinitionUseCase, saveFavoriteWordUseCase, logoutUseCase, getUserEmailUseCase);
    }
}